

package lab1;

import java.util.Map;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {


        System.out.println("Hello world");
        Display d = new Display();
        
        System.out.println(d);

        // Create a strategy for the agent
        Map<Agent.Direction, Double> strategy = Map.of(
            Agent.Direction.UP, 0.25,
            Agent.Direction.DOWN, 0.25,
            Agent.Direction.LEFT, 0.25,
            Agent.Direction.RIGHT, 0.25
        );
    
        // Create an agent with the strategy
        Agent agent = new Agent(strategy);
    
        // Initialize the agent's start and end positions
        agent.initstart(d);
        agent.initend(d);
    
        
        boolean moved = agent.move(agent.decideAction(), d);
        if (moved) {
            System.out.println("Agent moved to: " + agent.getLocation().getX() + ", " + agent.getLocation().getY());
        } else {
            System.out.println("Agent could not move.");
        }
    
        // Start moving the agent until it reaches the end
        System.out.println(agent.getStart().getX() + " " + agent.getStart().getY());
        System.out.println(agent.getEnd().getX() + " " + agent.getEnd().getY());
        agent.startmoving(d, agent);


    
    }

}

