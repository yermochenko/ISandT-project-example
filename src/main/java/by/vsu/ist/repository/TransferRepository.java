package by.vsu.ist.repository;

import by.vsu.ist.domain.Account;
import by.vsu.ist.domain.Transfer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TransferRepository extends BaseRepository {
	public Long create(Transfer transfer) throws SQLException {
		String sql = "INSERT INTO \"transfer\"(\"sender_id\", \"receiver_id\", \"sum\", \"purpose\") VALUES (?, ?, ?, ?)";
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			Optional<Account> sender = transfer.getSender();
			if(sender.isPresent()) {
				statement.setLong(1, sender.get().getId());
			} else {
				statement.setNull(1, Types.INTEGER);
			}
			Optional<Account> receiver = transfer.getReceiver();
			if(receiver.isPresent()) {
				statement.setLong(2, receiver.get().getId());
			} else {
				statement.setNull(2, Types.INTEGER);
			}
			statement.setLong(3, transfer.getSum());
			Optional<String> purpose = transfer.getPurpose();
			if(purpose.isPresent()) {
				statement.setString(4, purpose.get());
			} else {
				statement.setNull(4, Types.VARCHAR);
			}
			statement.executeUpdate();
			resultSet = statement.getGeneratedKeys();
			resultSet.next();
			return resultSet.getLong(1);
		} finally {
			try { Objects.requireNonNull(resultSet).close(); } catch(Exception ignored) {}
			try { Objects.requireNonNull(statement).close(); } catch(Exception ignored) {}
		}
	}

	public List<Transfer> readByAccount(Long accountId) throws SQLException {
		String sql = "SELECT \"id\", \"sender_id\", \"receiver_id\", \"sum\", \"date\", \"purpose\" FROM \"transfer\" WHERE \"sender_id\" = ? OR \"receiver_id\" = ?";
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = getConnection().prepareStatement(sql);
			statement.setLong(1, accountId);
			statement.setLong(2, accountId);
			resultSet = statement.executeQuery();
			List<Transfer> transfers = new ArrayList<>();
			while(resultSet.next()) {
				Transfer transfer = new Transfer();
				transfer.setId(resultSet.getLong("id"));
				Long senderId = resultSet.getLong("sender_id");
				if(!resultSet.wasNull()) {
					Account sender = new Account();
					sender.setId(senderId);
					transfer.setSender(sender);
				}
				Long receiverId = resultSet.getLong("receiver_id");
				if(!resultSet.wasNull()) {
					Account receiver = new Account();
					receiver.setId(receiverId);
					transfer.setReceiver(receiver);
				}
				transfer.setSum(resultSet.getLong("sum"));
				transfer.setDate(new java.util.Date(resultSet.getTimestamp("date").getTime()));
				transfer.setPurpose(resultSet.getString("purpose"));
				transfers.add(transfer);
			}
			return transfers;
		} finally {
			try { Objects.requireNonNull(resultSet).close(); } catch(Exception ignored) {}
			try { Objects.requireNonNull(statement).close(); } catch(Exception ignored) {}
		}
	}

	public Optional<Transfer> read(Long id) throws SQLException {
		String sql = "SELECT \"id\", \"sender_id\", \"receiver_id\", \"sum\", \"date\", \"purpose\" FROM \"transfer\" WHERE \"id\" = ?";
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = getConnection().prepareStatement(sql);
			statement.setLong(1, id);
			resultSet = statement.executeQuery();
			Transfer transfer = null;
			if(resultSet.next()) {
				transfer = new Transfer();
				transfer.setId(resultSet.getLong("id"));
				Long senderId = resultSet.getLong("sender_id");
				if(!resultSet.wasNull()) {
					Account sender = new Account();
					sender.setId(senderId);
					transfer.setSender(sender);
				}
				Long receiverId = resultSet.getLong("receiver_id");
				if(!resultSet.wasNull()) {
					Account receiver = new Account();
					receiver.setId(receiverId);
					transfer.setReceiver(receiver);
				}
				transfer.setSum(resultSet.getLong("sum"));
				transfer.setDate(new java.util.Date(resultSet.getTimestamp("date").getTime()));
				transfer.setPurpose(resultSet.getString("purpose"));
			}
			return Optional.ofNullable(transfer);
		} finally {
			try { Objects.requireNonNull(resultSet).close(); } catch(Exception ignored) {}
			try { Objects.requireNonNull(statement).close(); } catch(Exception ignored) {}
		}
	}

	public void update(Transfer transfer) {
		throw new UnsupportedOperationException("Will not be supported");
	}

	public void delete(Long id) {
		throw new UnsupportedOperationException("Will not be supported");
	}
}
