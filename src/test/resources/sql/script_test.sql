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