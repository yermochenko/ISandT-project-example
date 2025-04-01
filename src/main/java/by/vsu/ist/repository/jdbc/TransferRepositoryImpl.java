package by.vsu.ist.repository.jdbc;

import by.vsu.ist.domain.Account;
import by.vsu.ist.domain.Transfer;
import by.vsu.ist.repository.RepositoryException;
import by.vsu.ist.repository.TransferRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransferRepositoryImpl extends BaseRepository<Transfer> implements TransferRepository {
	public TransferRepositoryImpl() {
		super(
			"SELECT \"id\", \"sender_id\", \"receiver_id\", \"sum\", \"date\", \"purpose\" FROM \"transfer\" WHERE \"id\" = ?",
			"INSERT INTO \"transfer\"(\"sender_id\", \"receiver_id\", \"sum\", \"purpose\") VALUES (?, ?, ?, ?)",
			null,
			null
		);
	}

	@Override
	public List<Transfer> readByAccount(Long accountId) throws RepositoryException {
		String sql = "SELECT \"id\", \"sender_id\", \"receiver_id\", \"sum\", \"date\", \"purpose\" FROM \"transfer\" WHERE \"sender_id\" = ? OR \"receiver_id\" = ? ORDER BY \"date\" DESC";
		List<Transfer> transfers = new ArrayList<>();
		read(
			sql,
			statement -> {
				statement.setLong(1, accountId);
				statement.setLong(2, accountId);
			},
			transfers::add
		);
		return transfers;
	}

	@Override
	public void update(Transfer transfer) {
		throw new UnsupportedOperationException("Will not be supported");
	}

	@Override
	public void delete(Long id) {
		throw new UnsupportedOperationException("Will not be supported");
	}

	@Override
	protected Transfer buildFromResultSet(ResultSet resultSet) throws SQLException {
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
		return transfer;
	}

	@Override
	protected void fillInsertPreparedStatement(PreparedStatement statement, Transfer transfer) throws SQLException {
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
	}

	@Override
	protected void fillUpdatePreparedStatement(PreparedStatement statement, Transfer transfer) {
		throw new UnsupportedOperationException("Will not be supported");
	}
}
