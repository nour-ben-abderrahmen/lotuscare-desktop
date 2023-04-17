package Interfaces;

import Models.Don;

import java.sql.SQLException;
import java.util.List;

public interface DonService {
    public void addDon(Don don) throws SQLException;
    public List<Don> getAllDons() throws SQLException;
}
