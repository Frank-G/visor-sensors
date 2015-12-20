package de.uniulm.omi.cloudiator.visor;

import de.uniulm.omi.cloudiator.visor.monitoring.*;

import java.sql.*;

/**
 * Created by Frank on 20.12.2015.
 */
public class GenericMySQLProbe implements Sensor {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    private String username;
    private String password;
    private String statement;
    private String dbUri;

    @Override
    public void init() throws SensorInitializationException {
        //
    }

    @Override
    public void setMonitorContext(MonitorContext monitorContext) throws InvalidMonitorContextException {
        password = monitorContext.getValue("password");
        username = monitorContext.getValue("username");
        statement = monitorContext.getValue("statement");
        dbUri = monitorContext.getValue("uri");
    }

    @Override
    public Measurement getMeasurement() throws MeasurementNotAvailableException {
        try{
            Connection conn = null;
            Statement stmt = null;

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(dbUri, username, password);

            stmt.close();
            conn.close();

            PreparedStatement preparedStatement = conn.prepareStatement(statement);

            final ResultSet resultSet = preparedStatement.executeQuery();

            Double value = Double.valueOf(resultSet.getObject(0).toString());

            return new MeasurementImpl(System.currentTimeMillis(), value);
        } catch(ClassNotFoundException | SQLException e){
            throw new MeasurementNotAvailableException(e);
        }
    }
}