-- MySQL Workbench Forward Engineering
-- -----------------------------------------------------
-- Schema periodicals_website
-- -----------------------------------------------------
-- Сайт із періодичними виданнями
--  Сайт надає доступ до періодичних видань, підтримує реєстрацію нових користувачів та зберігає інформацію про них.
--  Є дві ролі: читач та адміністратор. Читачі мають баланс (спочатку дорівнює 0), який можна поповнювати.
--  Читач може вибрати видання зі списку, сформованого на підставі типу та теми видання, залишити відгук на нього,
--  оформити передплату, вартість якої вираховується з його балансу.
--  При перериванні підписки частина коштів повертається баланс.
--  Також система зберігає дані про операції з балансом, щоб читач міг переглянути історію.
--  Видання мають рейтинг, який вираховується з оцінок відгуків.
-- ------------------------------------------------------

DROP SCHEMA IF EXISTS periodicals_website;

-- -----------------------------------------------------
-- Schema periodicals_website
--
-- Сайт із періодичними виданнями
-- Сайт надає доступ до періодичних видань, підтримує реєстрацію нових користувачів та зберігає інформацію про них.
-- Є дві ролі: читач та адміністратор. Читачі мають баланс (спочатку дорівнює 0), який можна поповнювати.
-- Читач може вибрати видання зі списку, сформованого на основі типу та теми видання, залишити відгук на нього,
-- оформити передплату, вартість якої вираховується з його балансу.
-- При перериванні підписки частина коштів повертається на баланс.
-- Також система зберігає дані про операції з балансом, щоб читач міг переглянути історію.
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS periodicals_website DEFAULT CHARACTER SET utf8;
USE periodicals_website;

-- -----------------------------------------------------
-- Table periodicals_website.themes
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS periodicals_website.themes
(
    id           SMALLINT(6)  NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'ID темм.',
    default_name VARCHAR(150) NOT NULL COMMENT 'Назва теми'
)
    DEFAULT CHARACTER SET = utf8
    COMMENT = 'Теми періодичних виданнь';


-- -----------------------------------------------------
-- Table periodicals_website.types
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS periodicals_website.types
(
    id SMALLINT(6)  NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'ID типу',
    default_name VARCHAR(150) NOT NULL COMMENT 'Назва типу (газета, журнал)'
)
    DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table periodicals_website.publications
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS periodicals_website.publications
(
    id           INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT 'ID видання',
    id_theme    SMALLINT(6) NULL DEFAULT NULL COMMENT 'Тема',
    id_type      SMALLINT(6) NULL DEFAULT NULL COMMENT 'Тип',
    price        DECIMAL(8, 2) NOT NULL COMMENT 'Ціна підписки на місяць',
    picture_path VARCHAR(100) NULL,
    INDEX fk_publications_themes_idx (id_theme ASC),
    INDEX fk_publications_types_idx (id_type ASC),
    CONSTRAINT fk_publications_themes
        FOREIGN KEY (id_theme)
            REFERENCES periodicals_website.themes (id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE,
    CONSTRAINT fk_publications_types
        FOREIGN KEY (id_type)
            REFERENCES periodicals_website.types (id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE
)
    DEFAULT CHARACTER SET = utf8
    COMMENT = 'Таблиця для зберігання даних про періодичні видання';


-- -----------------------------------------------------
-- Table periodicals_website.publications_local
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS periodicals_website.publications_local
(
    id_publication INT(11) NOT NULL COMMENT 'ID видання',
    locale        ENUM ('uk_UA', 'en_US') NOT NULL COMMENT 'Локаль',
    name           VARCHAR(200) NOT NULL COMMENT 'Назва видання',
    description    VARCHAR(2000) NOT NULL COMMENT 'Опис видання',
    PRIMARY KEY (id_publication, locale),
    CONSTRAINT fk_puplications_locale_puplications
        FOREIGN KEY (id_publication)
            REFERENCES periodicals_website.publications (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
)
    DEFAULT CHARACTER SET = utf8
    COMMENT = 'Локалізовані параметри періодичних видань';


-- -----------------------------------------------------
-- Table periodicals_website.roles
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS periodicals_website.roles
(
    id   TINYINT(4)  NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'ID ролі',
    name VARCHAR(20) NOT NULL COMMENT 'Ім\'я ролі'
)
    DEFAULT CHARACTER SET = utf8
    COMMENT = 'Таблиця для ролей користувача';


-- -----------------------------------------------------
-- Table periodicals_website.users
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS periodicals_website.users
(
    id       INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'ID коримтувача',
    login    VARCHAR(50) NOT NULL COMMENT 'Логін',
    password VARCHAR(200) NOT NULL COMMENT 'Пароль.',
    name     VARCHAR(45) NOT NULL COMMENT 'Ім\'я користувача',
    surname  VARCHAR(45) NOT NULL COMMENT 'Призвіще користувача',
    email    VARCHAR(50) NOT NULL COMMENT 'Email',
    balance  DECIMAL(15, 2) UNSIGNED NULL DEFAULT '0.00' COMMENT 'Баланс користувача. Спочатку рівний нулю. Не може бути від\'ємним.',
    id_role  TINYINT(4) NULL DEFAULT 2 COMMENT 'ID ролі користувача. 1 - для читачів за замовчуванням',
    UNIQUE INDEX login_UNIQUE (login ASC),
    UNIQUE INDEX email_UNIQUE (email ASC),
    INDEX fk_users_roles_idx (id_role ASC),
    CONSTRAINT fk_users_roles
        FOREIGN KEY (id_role)
            REFERENCES periodicals_website.roles (id)
            ON UPDATE CASCADE
)
    DEFAULT CHARACTER SET = utf8
    COMMENT = 'Таблиця для збереження інформації про корстувачів';


-- -----------------------------------------------------
-- Table periodicals_website.reviews
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS periodicals_website.reviews
(
    id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'ID відгуку',
    id_user INT(11) NULL DEFAULT NULL COMMENT 'Користувач який написав відгук',
    id_publication INT(11) NULL DEFAULT NULL COMMENT 'Видання, на яке написаний відгук',
    date_of_publication DATETIME NOT NULL COMMENT 'Дата написання',
    text VARCHAR(4000) NOT NULL COMMENT 'Тіло відгука',
    mark TINYINT(4) NULL DEFAULT '0' COMMENT 'оцінка (від 0 до 5)',
    INDEX fk_reviews_users_idx (id_user ASC),
    INDEX fk_reviews_publications_idx (id_publication ASC),
    CONSTRAINT fk_reviews_publications
        FOREIGN KEY (id_publication)
            REFERENCES periodicals_website.publications (id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE,
    CONSTRAINT fk_reviews_users
        FOREIGN KEY (id_user)
            REFERENCES periodicals_website.users (id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE
)
    DEFAULT CHARACTER SET = utf8
    COMMENT = 'Відгуки про видання';


-- -----------------------------------------------------
-- Table periodicals_website.subscriptions
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS periodicals_website.subscriptions
(
    id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'ID підписки',
    id_publication INT(11) NULL DEFAULT NULL COMMENT 'Публікація, на яку оформлена підписка',
    id_user INT(11) NULL DEFAULT NULL COMMENT 'користувач, який підписався',
    start_date DATE NOT NULL COMMENT 'Початок підписки',
    end_date DATE NOT NULL COMMENT 'Кінець підписки',
    price DECIMAL(15, 2) NOT NULL COMMENT 'Ціна',
    status ENUM ('ACTIVE', 'EXPIRED', 'TERMINATED') NULL DEFAULT 'ACTIVE' COMMENT 'Статус: активна, пройшла, перервана',
    INDEX fk_subscriptions_publications_idx (id_publication ASC),
    INDEX fk_subscriptions_users_idx (id_user ASC),
    CONSTRAINT fk_subscriptions_publications
        FOREIGN KEY (id_publication)
            REFERENCES periodicals_website.publications (id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE,
    CONSTRAINT fk_subscriptions_users
        FOREIGN KEY (id_user)
            REFERENCES periodicals_website.users (id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE
)
    DEFAULT CHARACTER SET = utf8
    COMMENT = 'Таблиця для збереження інформації про підписки';


-- -----------------------------------------------------
-- Table periodicals_website.themes_local
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS periodicals_website.themes_local
(
    id_theme SMALLINT(6) NOT NULL COMMENT 'ID теми',
    locale ENUM ('uk_UA', 'en_US') NOT NULL COMMENT 'Локаль. Українська, Англійска',
    name VARCHAR(150) NOT NULL COMMENT 'Название темы',
    PRIMARY KEY (id_theme, locale),
    CONSTRAINT fk_themes_local_themes
        FOREIGN KEY (id_theme)
            REFERENCES periodicals_website.themes (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
)
    DEFAULT CHARACTER SET = utf8
    COMMENT = 'Локалізовані назви тем';


-- -----------------------------------------------------
-- Table periodicals_website.balance_operations
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS periodicals_website.balance_operations
(
    id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'ID операції',
    id_user INT(11) NULL DEFAULT NULL COMMENT 'користувач, з балансом якого проводиться операція',
    date DATETIME NOT NULL COMMENT 'Дата операції',
    sum DECIMAL(10, 0) NOT NULL COMMENT 'Сумма списання/поповнення балансу',
    type ENUM ('PAYMENT_OF_SUBSCRIPTION', 'REFUND', 'BALANCE_REPLENISHMENT') NOT NULL COMMENT 'Тип оперції з балансом: оплата підписки, повернення грошей під час переривання підписки, поповнення балансу',
    INDEX fk_transactions_users_idx (id_user ASC),
    CONSTRAINT fk_transactions_users
        FOREIGN KEY (id_user)
            REFERENCES periodicals_website.users (id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE
)
    DEFAULT CHARACTER SET = utf8
    COMMENT = 'Таблиця для операцій з балансом користувача';


-- -----------------------------------------------------
-- Table periodicals_website.types_local
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS periodicals_website.types_local
(
    id_type SMALLINT(6) NOT NULL COMMENT 'ID типа',
    locale  ENUM ('uk_UA', 'en_US') NOT NULL COMMENT 'Локаль. Українська, англійська',
    name    VARCHAR(150) NOT NULL COMMENT 'Назва типу',
    PRIMARY KEY (id_type, locale),
    CONSTRAINT fk_types_local_types
        FOREIGN KEY (id_type)
            REFERENCES periodicals_website.types (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
)
    DEFAULT CHARACTER SET = utf8
    COMMENT = 'Локализовані назви типів';


-- -----------------------------------------------------
-- Table `periodicals_website`.`issues`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS periodicals_website.issues
(
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    date_of_publication DATE NOT NULL,
    publication_id INT NOT NULL,
    file VARCHAR(100) NOT NULL,
    description VARCHAR(200) NOT NULL,
    INDEX fk_issues_publications_idx (publication_id ASC),
    CONSTRAINT fk_issues_publications
    FOREIGN KEY (publication_id)
    REFERENCES periodicals_website.publications (id)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT);


-- -----------------------------------------------------
-- Data for table `periodicals_website`.`themes`
-- -----------------------------------------------------
START TRANSACTION;
USE `periodicals_website`;
INSERT INTO `periodicals_website`.`themes` (`id`, `default_name`) VALUES (1, 'Language and literature');
INSERT INTO `periodicals_website`.`themes` (`id`, `default_name`) VALUES (2, 'Health. Medicine');
INSERT INTO `periodicals_website`.`themes` (`id`, `default_name`) VALUES (3, 'Scientific and popular science publications');

COMMIT;


-- -----------------------------------------------------
-- Data for table `periodicals_website`.`types`
-- -----------------------------------------------------
START TRANSACTION;
USE `periodicals_website`;
INSERT INTO `periodicals_website`.`types` (`id`, `default_name`) VALUES (1, 'Newspaper');
INSERT INTO `periodicals_website`.`types` (`id`, `default_name`) VALUES (2, 'Magazine');

COMMIT;


-- -----------------------------------------------------
-- Data for table `periodicals_website`.`publications`
-- -----------------------------------------------------
START TRANSACTION;
USE `periodicals_website`;
INSERT INTO `periodicals_website`.`publications` (`id`, `id_theme`, `id_type`, `price`, `picture_path`) VALUES (63109,  1, 1, 329.50, 'literatura.jpg');
INSERT INTO `periodicals_website`.`publications` (`id`, `id_theme`, `id_type`, `price`, `picture_path`) VALUES (63103, 2, 2, 200.12, 'emergency.jpg');
INSERT INTO `periodicals_website`.`publications` (`id`, `id_theme`, `id_type`, `price`, `picture_path`) VALUES (63241,  3, 2, 449.50, 'information.jpg');

COMMIT;


-- -----------------------------------------------------
-- Data for table `periodicals_website`.`publications_local`
-- -----------------------------------------------------
START TRANSACTION;
USE `periodicals_website`;
INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (63109, 'en_US', 'Literary Ukraine', '\"Literary Ukraine\" - is a weekly newspaper of writers of Ukraine, a specialized literary and critical publication.');
INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (63109, 'uk_UA', 'Літературна Україна', '\"Літературна Україна\" - щотижнева газета письменників України, фахове літературно-критичне видання.');
INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (63103, 'en_US', 'EMERGENCY MEDICINE', 'It is worth noting that the magazine summarizes the personal fruitful experience of Professor I.S. Zozuli as the head of the department of emergency conditions of the National Medical Academy of Postgraduate Education (NMAPO) named after P.L. Shupyk and at the same time vice-rector for scientific work of this multi-functional higher medical educational institution. So we have a perfect textbook for trainee doctors, intern doctors and students of higher medical educational institutions, approved by the Ministry of Education and Science of Ukraine.');
INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (63103, 'uk_UA', 'МЕДИЦИНА НЕВIДКЛАДНИХ СТАНIВ', 'Варто зазначити, що у журналі узагальнено й особистий плідний досвід професора І.С. Зозулі як завідувача кафедри невідкладних станів Національної медичної академії післядипломної освіти (НМАПО) ім. П.Л. Шупика й одночасно проректора з наукової роботи цього поліфункціонального вищого медичного навчального закладу. Тож маємо і досконалий підручник для лікарів-курсантів, лікарів-інтернів і студентів вищих медичних навчальних закладів, затверджений Міністерством освіти і науки України.');
INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (63241, 'en_US', 'Information Technology: Computer Science, Software Engineering, Cyber Security', 'This magazine deals with modern IT technologies, breakthroughs in computer science, software development and cyber security.');
INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (63241, 'uk_UA', 'Інформаційні технології: інформатика, розробка програмного забезпечення, кібербезпека', 'У цьому журналі йдеться про сучасні іт технології, прориви в комп'' ютерних науках, розробка програмного забезпечення та кібербезпеку.');

COMMIT;


-- -----------------------------------------------------
-- Data for table `periodicals_website`.`roles`
-- -----------------------------------------------------
START TRANSACTION;
USE `periodicals_website`;
INSERT INTO `periodicals_website`.`roles` (`id`, `name`) VALUES (1, 'ADMIN');
INSERT INTO `periodicals_website`.`roles` (`id`, `name`) VALUES (2, 'CUSTOMER');
INSERT INTO periodicals_website.roles (id, name) VALUES (3, 'BLOCK');

COMMIT;


-- -----------------------------------------------------
-- Data for table `periodicals_website`.`users`
-- -----------------------------------------------------
START TRANSACTION;
USE `periodicals_website`;
INSERT INTO `periodicals_website`.`users` (`id`, `login`, `password`, `name`, `surname`, `email`, `balance`, `id_role`) VALUES (1, 'admin', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec', 'Admin', 'Adminovich', 'admin@admin.com', 0, 1);
INSERT INTO `periodicals_website`.`users` (`id`, `login`, `password`, `name`, `surname`, `email`, `balance`, `id_role`) VALUES (2, 'user1', '3627909a29c31381a071ec27f7c9ca97726182aed29a7ddd2e54353322cfb30abb9e3a6df2ac2c20fe23436311d678564d0c8d305930575f60e2d3d048184d79', 'User1', 'Surname1', 'user1@user.com', 0, 2);
INSERT INTO `periodicals_website`.`users` (`id`, `login`, `password`, `name`, `surname`, `email`, `balance`, `id_role`) VALUES (3, 'user2', '3627909a29c31381a071ec27f7c9ca97726182aed29a7ddd2e54353322cfb30abb9e3a6df2ac2c20fe23436311d678564d0c8d305930575f60e2d3d048184d79', 'User2', 'Surname2', 'user2@user.com', 0, 2);
INSERT INTO `periodicals_website`.`users` (`id`, `login`, `password`, `name`, `surname`, `email`, `balance`, `id_role`) VALUES (4, 'user3', '3627909a29c31381a071ec27f7c9ca97726182aed29a7ddd2e54353322cfb30abb9e3a6df2ac2c20fe23436311d678564d0c8d305930575f60e2d3d048184d79', 'User3', 'Surname3', 'user3@user.com', 0, 2);
INSERT INTO `periodicals_website`.`users` (`id`, `login`, `password`, `name`, `surname`, `email`, `balance`, `id_role`) VALUES (5, 'user4', '3627909a29c31381a071ec27f7c9ca97726182aed29a7ddd2e54353322cfb30abb9e3a6df2ac2c20fe23436311d678564d0c8d305930575f60e2d3d048184d79', 'User4', 'Surname4', 'user4@user.com', 0, 2);
INSERT INTO `periodicals_website`.`users` (`id`, `login`, `password`, `name`, `surname`, `email`, `balance`, `id_role`) VALUES (6, 'VladSV', '3627909a29c31381a071ec27f7c9ca97726182aed29a7ddd2e54353322cfb30abb9e3a6df2ac2c20fe23436311d678564d0c8d305930575f60e2d3d048184d79', 'Vladislav', 'Sevashko', 'vlad@gmail.com', 0, 2);

COMMIT;


-- -----------------------------------------------------
-- Data for table `periodicals_website`.`reviews`
-- -----------------------------------------------------
START TRANSACTION;
USE `periodicals_website`;
INSERT INTO `periodicals_website`.`reviews` (`id`, `id_user`, `id_publication`, `date_of_publication`, `text`, `mark`) VALUES (DEFAULT, 6, 63109, '2018-03-01 14:24:38', 'Добре!', 5);
INSERT INTO `periodicals_website`.`reviews` (`id`, `id_user`, `id_publication`, `date_of_publication`, `text`, `mark`) VALUES (DEFAULT, 6, 63109, '2018-03-01 14:24:38', 'Добре!', 4);
INSERT INTO `periodicals_website`.`reviews` (`id`, `id_user`, `id_publication`, `date_of_publication`, `text`, `mark`) VALUES (DEFAULT, 6, 64121, '2018-03-01 14:24:38', 'Добре!', 3);

COMMIT;


-- -----------------------------------------------------
-- Data for table `periodicals_website`.`subscriptions`
-- -----------------------------------------------------
START TRANSACTION;
USE `periodicals_website`;
INSERT INTO `periodicals_website`.`subscriptions` (`id`, `id_publication`, `id_user`, `start_date`, `end_date`, `price`, `status`) VALUES (1, 63241, 6, '2019-01-01', '2021-01-01', 10788, 'EXPIRED');
INSERT INTO `periodicals_website`.`subscriptions` (`id`, `id_publication`, `id_user`, `start_date`, `end_date`, `price`, `status`) VALUES (2, 63103, 6, '2022-04-12', '2022-10-12', 1200.72, 'TERMINATED');
INSERT INTO `periodicals_website`.`subscriptions` (`id`, `id_publication`, `id_user`, `start_date`, `end_date`, `price`, `status`) VALUES (3, 63109, 6, '2022-07-30', '2023-07-30', 3954, 'ACTIVE');
INSERT INTO `periodicals_website`.`subscriptions` (`id`, `id_publication`, `id_user`, `start_date`, `end_date`, `price`, `status`) VALUES (4, 63241, 6, '2022-07-30', '2024-07-30', 10788, 'ACTIVE');

COMMIT;


-- -----------------------------------------------------
-- Data for table `periodicals_website`.`themes_local`
-- -----------------------------------------------------
START TRANSACTION;
USE `periodicals_website`;
INSERT INTO `periodicals_website`.`themes_local` (`id_theme`, `locale`, `name`) VALUES (1, 'en_US', 'Language and literature');
INSERT INTO `periodicals_website`.`themes_local` (`id_theme`, `locale`, `name`) VALUES (1, 'uk_UA', 'Мова та література');
INSERT INTO `periodicals_website`.`themes_local` (`id_theme`, `locale`, `name`) VALUES (2, 'en_US', 'Health. Medicine.');
INSERT INTO `periodicals_website`.`themes_local` (`id_theme`, `locale`, `name`) VALUES (2, 'uk_UA', 'Здоров\'я. Медицина.');
INSERT INTO `periodicals_website`.`themes_local` (`id_theme`, `locale`, `name`) VALUES (3, 'en_US', 'Scientific and popular science publications');
INSERT INTO `periodicals_website`.`themes_local` (`id_theme`, `locale`, `name`) VALUES (3, 'uk_UA', 'Наукові і науково-популярні видання');

COMMIT;


-- -----------------------------------------------------
-- Data for table `periodicals_website`.`balance_operations`
-- -----------------------------------------------------
START TRANSACTION;
USE `periodicals_website`;
INSERT INTO `periodicals_website`.`balance_operations` (`id`, `id_user`, `date`, `sum`, `type`) VALUES (1, 3, '2019-01-01 12:12:12', 10788, 'PAYMENT_OF_SUBSCRIPTION');
INSERT INTO `periodicals_website`.`balance_operations` (`id`, `id_user`, `date`, `sum`, `type`) VALUES (2, 2, '2022-04-12 12:12:12', 1200.72, 'REFUND');
INSERT INTO `periodicals_website`.`balance_operations` (`id`, `id_user`, `date`, `sum`, `type`) VALUES (3, 6, '2022-07-30 12:12:12', 3954, 'PAYMENT_OF_SUBSCRIPTION');
INSERT INTO `periodicals_website`.`balance_operations` (`id`, `id_user`, `date`, `sum`, `type`) VALUES (4, 6, '2022-07-30 12:12:12', 10788, 'BALANCE_REPLENISHMENT');

COMMIT;


-- -----------------------------------------------------
-- Data for table `periodicals_website`.`types_local`
-- -----------------------------------------------------
START TRANSACTION;
USE `periodicals_website`;
INSERT INTO `periodicals_website`.`types_local` (`id_type`, `locale`, `name`) VALUES (1, 'en_US', 'Newspaper');
INSERT INTO `periodicals_website`.`types_local` (`id_type`, `locale`, `name`) VALUES (1, 'uk_UA', 'Газета');
INSERT INTO `periodicals_website`.`types_local` (`id_type`, `locale`, `name`) VALUES (2, 'en_US', 'Magazine');
INSERT INTO `periodicals_website`.`types_local` (`id_type`, `locale`, `name`) VALUES (2, 'uk_UA', 'Журнал');

COMMIT;


-- -----------------------------------------------------
-- Data for table `periodicals_website`.`issues`
-- -----------------------------------------------------
START TRANSACTION;
USE `periodicals_website`;
INSERT INTO `periodicals_website`.`issues` (`id`, `date_of_publication`, `publication_id`, `file`, `description`) VALUES (1, '2022-08-01', 63241, '63241/2022/Хакер 2022 01-02(227).pdf', 'Видання №227');
INSERT INTO `periodicals_website`.`issues` (`id`, `date_of_publication`, `publication_id`, `file`, `description`) VALUES (2, '2022-08-01', 63241, '63241/2022/Хакер 2022 03(228).pdf', 'Видання №228');
INSERT INTO `periodicals_website`.`issues` (`id`, `date_of_publication`, `publication_id`, `file`, `description`) VALUES (3, '2022-08-01', 63241, '63241/2022/Хакер 2022 04(229).pdf', 'Видання №229');
INSERT INTO `periodicals_website`.`issues` (`id`, `date_of_publication`, `publication_id`, `file`, `description`) VALUES (4, '2022-08-01', 63241, '63241/2022/Хакер 2022 01-05(230).pdf', 'Видання №230');
INSERT INTO `periodicals_website`.`issues` (`id`, `date_of_publication`, `publication_id`, `file`, `description`) VALUES (5, '2022-08-01', 63241, '63241/2022/Хакер 2022 06(231).pdf', 'Видання №231');

COMMIT;

