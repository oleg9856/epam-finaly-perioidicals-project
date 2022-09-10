package com.gmail.fursovych20.db.dao.impl;

import com.gmail.fursovych20.db.dao.ReviewDAO;
import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.entity.Review;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * ReviewOperationImpl implementation for manipulating review in the database
 *
 * @author O. Fursovych
 */

public class ReviewDAOImpl implements ReviewDAO {

	private static final Logger LOG = LogManager.getLogger(ReviewDAOImpl.class);
	private final DataSource dataSource;
	private static final String READ_REVIEWS_FOR_PUBLICATION = "SELECT id, id_user, id_publication, date_of_publication, text, mark FROM reviews WHERE id_publication=? ORDER BY date_of_publication DESC";
	private static final String READ_REVIEW = "SELECT id, id_user, id_publication, date_of_publication, text, mark FROM reviews WHERE id=?";
	private static final String INSERT_REVIEW = "INSERT INTO `periodicals_website`.`reviews` (`id_user`, `id_publication`, `date_of_publication`, `text`, `mark`) VALUES (?, ?, ?, ?, ?);";
	private static final String UPDATE_REVIEW = "UPDATE `periodicals_website`.`reviews` SET `text`=?, `mark`=? WHERE `id`=?";
	private static final String DELETE_REVIEW = "DELETE FROM `periodicals_website`.`reviews` WHERE `id`=?";
			
	private static final String ID = "id";
	private static final String ID_USER = "id_user";
	private static final String ID_PUBLICATION = "id_publication";
	private static final String DATE_OF_PUBLICATION = "date_of_publication";
	private static final String TEXT = "text";
	private static final String MARK = "mark";

	public ReviewDAOImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<Review> findReviewByPublicationId(int publicationId) throws DAOException {
		List<Review> reviews = new ArrayList<>();
		try(Connection connection = getConnection()){
			try(PreparedStatement ps = connection.prepareStatement(READ_REVIEWS_FOR_PUBLICATION)) {
				ps.setInt(1, publicationId);
				try (ResultSet resultSet = ps.executeQuery()) {
					while (resultSet.next()) {
						Review review = getReview(resultSet);
						reviews.add(review);
					}
					LOG.info("Find review by publicationID --> {}", publicationId);
				}
			}
		} catch (SQLException e) {
			LOG.error("(SQLException)Can`t find reading reviews");
			throw new DAOException("Exception reading reviews", e);
		}
		return reviews;
	}
	
	@Override
	public boolean create(Review review) throws DAOException {
		try(Connection connection = getConnection()){
			try(PreparedStatement ps = connection.prepareStatement(INSERT_REVIEW, Statement.RETURN_GENERATED_KEYS)) {
				ps.setInt(1, review.getUserId());
				ps.setInt(2, review.getPublicationId());
				ps.setTimestamp(3, Timestamp.valueOf(review.getDateOfPublication().atStartOfDay()));
				ps.setString(4, review.getText());
				ps.setByte(5, review.getMark());
				int result = ps.executeUpdate();
				if (result > 0) {
					try(ResultSet resultSet = ps.getGeneratedKeys()) {
						resultSet.next();
						review.setId(resultSet.getInt(1));
					}
				}

				LOG.info("Create review successfully!");
				return result != 0;
			}
		} catch (SQLException e) {
			LOG.error("(SQLException)Can`t create review", e);
			throw new DAOException("Exception creating review", e);
		}
	}

	@Override
	public boolean update(Review review) throws DAOException {
		try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_REVIEW)
		){
			ps.setString(1, review.getText());
			ps.setByte(2, review.getMark());
			ps.setInt(3, review.getId());

			LOG.info("Update review successfully!");
			return ps.executeUpdate() != 0;
		} catch (SQLException e) {
			LOG.error("(SQLException)Can`t update review",e);
			throw new DAOException("Exception updating review", e);
		}		
	}

	@Override
	public boolean delete(int id) throws DAOException {
		try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_REVIEW)
		){
			ps.setInt(1, id);

			LOG.info("Delete review by ID successfully --> {}",id);
			return ps.executeUpdate() != 0;
		} catch (SQLException e) {
			LOG.error("(SQLException)Can`t delete review by ID",e);
			throw new DAOException("Exception deleting review", e);
		}
	}
	
	@Override
	public Review findReviewById(int id) throws DAOException {
		try(Connection connection = getConnection()){
			try(PreparedStatement ps  = connection.prepareStatement(READ_REVIEW)) {
				ps.setInt(1, id);
				try (ResultSet resultSet = ps.executeQuery()) {
					if (resultSet.next()) {
						LOG.info("Find review by ID successfully --> {}", id);
						return getReview(resultSet);
					}
				}
			}
		} catch (SQLException e) {
			LOG.error("(SQLException)Can`t find review by id",e);
			throw new DAOException("Exception reading reviews", e);
		}
		return null;
	}

	private Review getReview(ResultSet resultSet) throws SQLException {
		return new Review.Builder()
				.setId(resultSet.getInt(ID))
				.setUserId(resultSet.getInt(ID_USER))
				.setPublicationId(resultSet.getInt(ID_PUBLICATION))
				.setDateOfPublication(resultSet.getTimestamp(DATE_OF_PUBLICATION).toLocalDateTime().toLocalDate())
				.setText(resultSet.getString(TEXT))
				.setMark(resultSet.getByte(MARK))
				.build();
	}

	private Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

}
