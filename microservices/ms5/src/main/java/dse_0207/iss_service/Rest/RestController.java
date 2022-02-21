package dse_0207.iss_service.Rest;

import dse_0207.iss_service.Controller.DeviceController;
import dse_0207.iss_service.EventSimulation.Simulators.ESimulatorType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.web.bind.annotation.RestController

public class RestController {

    private static DeviceController dc = new DeviceController();


    @GetMapping("/api/v1/terminate")
    public ResponseEntity<String> terminate() {
        System.exit(0);

        return new ResponseEntity<>(
                "Shutting down device...",
                HttpStatus.OK);
    }

    @PostMapping("/api/v1/performSimulation")
    public ResponseEntity<String> performSimulation(@RequestParam Integer duration, @RequestParam String type,
                                                    @RequestParam("threads") Integer nThreads) {
        if (duration <= 0) {
            return new ResponseEntity<>("Time is invalid.", HttpStatus.BAD_REQUEST);
        }

        ESimulatorType simulationType = ESimulatorType.getTypeByName(type);
        if (simulationType != ESimulatorType.temperature && simulationType != ESimulatorType.weather) {
            return new ResponseEntity<>("Allowed device names: 'weather' or 'temperature'", HttpStatus.BAD_REQUEST);
        }

        dc.instantiateDevice(simulationType);
        long startTime = System.currentTimeMillis();
        long endTime = startTime + duration;
        try {
            dc.startDevice(endTime, nThreads);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
