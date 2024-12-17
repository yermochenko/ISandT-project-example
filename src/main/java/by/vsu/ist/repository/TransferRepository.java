package by.vsu.ist.repository;

import by.vsu.ist.domain.Transfer;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface TransferRepository {
	Long create(Transfer transfer) throws SQLException;

	List<Transfer> readByAccount(Long accountId) throws SQLException;

	Optional<Transfer> read(Long id) throws SQLException;

	void update(Transfer transfer);

	void delete(Long id);
}
