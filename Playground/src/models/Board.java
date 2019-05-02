package models;

import java.util.*;

public class Board {
    public enum State {
        HEAD,
        TAIL,
        CONDUCTOR,
        EMPTY;

        private static final List<State> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
        private static final int SIZE = VALUES.size();
        private static final Random RANDOM = new Random();

        public static State randomState() {
            return VALUES.get(RANDOM.nextInt(SIZE));
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<State> getStates() {
        return states;
    }

    private final int width;
    private final int height;

    private final List<State> states;

    private static final Random random = new Random();

    /**
     * Create models of given size with random fields
     *
     * @param sizeX
     * @param sizeY
     */
    public Board(int sizeX, int sizeY) {
        width = sizeX;
        height = sizeY;
        states = new ArrayList<State>(getSize());

        randomizeFields();
    }

    /**
     * Default constructor for automatic deserialization
     */
    public Board() {
        width = 0;
        height = 0;
        states = new ArrayList<State>();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(getSize() + 20);

        result.append("Width: ").append(width).append("\n");
        result.append("Height: ").append(height).append("\n");

        Iterator<State> currentState = states.iterator();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
//                result.append(currentState.next().toString());
                result.append(states.get(i * width + j));
                if (j != width - 1)
                    result.append(" ");
            }
            if (i != height - 1)
                result.append("\n");
        }

        return result.toString();
    }

    /**
     * Returns how many elements are on models
     *
     * @return Number of elements in all dimensions
     */
    private int getSize() {
        return width * height;
    }

    /**
     * Sets all fields to random states
     */
    private void randomizeFields() {
        states.clear();
        for (int i = 0; i < getSize(); i++) {
            states.add(State.randomState());
        }
    }
}
