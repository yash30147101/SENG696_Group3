pack org.team.agent;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class UniversityPortalAgent extends Agent {
    @Override
    protected void setup() {
        System.out.println("Inside UniversityPortal Agent ... ");
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                switch ("Appointment") {
                    case "Appointment":
                        AppointmentJadeAgent appointmentJadeAgent = new AppointmentJadeAgent();
                        appointmentJadeAgent.setup();
                        break;
                    case "Cancellation":
                        CancellationAgent cancellationAgent = new CancellationAgent();
                        cancellationAgent.setup();
                        break;
                }

            }
        });
    }
}
