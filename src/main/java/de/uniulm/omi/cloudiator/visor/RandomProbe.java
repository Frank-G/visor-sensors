package de.uniulm.omi.cloudiator.visor;


import de.uniulm.omi.cloudiator.visor.monitoring.*;

import java.util.Random;

public class RandomProbe implements Sensor {
    private final Random random = new Random();
    private Long min;
    private Long max;

    @Override
    public void init() throws SensorInitializationException {
        //
    }

    @Override
    public void setMonitorContext(MonitorContext monitorContext) throws InvalidMonitorContextException {
        min = Long.valueOf(monitorContext.getOrDefault("min", "0"));
        max = Long.valueOf(monitorContext.getOrDefault("max", "10"));
    }

    @Override
    public Measurement getMeasurement() throws MeasurementNotAvailableException {
        return new MeasurementImpl(System.currentTimeMillis(), min + random.nextDouble() * (max - min));
    }

}
