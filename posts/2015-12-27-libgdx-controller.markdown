---
title: LibGDX Game Controller 
author: Kenny Cason
tags: java, libgdx, game
---

As with any low level code, eventually you get tired of working with it and wrap up common behavior. In my case I wanted to design for a few things:

1. A native interface for simultaneously handling both keyboard and logitech controller input. 
2. A button mapper to easily allow for custom mappings.
3. Record time when buttons are pressed to allow for easier support for capturing button combinations.

We'll start with our basic definition of buttons. This could be expanded depending on needs. Given my love of classic SNES style games, this is sufficient.
```{.java .numberLines startFrom="1"}
public enum Button {
    DPAD_UP,
    DPAD_DOWN,
    DPAD_LEFT,
    DPAD_RIGHT,

    START,
    SELECT,

    A,
    B,
    X,
    Y,

    L1,
    L2,
    R1,
    R2
}
```

Now that we have our Button "interface" defined, we need to create mappings to the internal integer key codes. The reason this is important is because different input method's keys may map to different values. The key codes for a logitech controller is significantly different than a computer's keyboard key codes. This abstraction also allows a user to easily map keys differently, for example, inverting up and down, or jump and run.
```{.java .numberLines startFrom="1"}
public class ButtonMapper<V> {

    private final Map<Button, V> buttonMap = new HashMap<>();

    public V get(final Button button) {
        return buttonMap.get(button);
    }

    public void map(final Button button, final V value) {
        buttonMap.put(button, value);
    }

    public static ButtonMapper<Integer> defaultKeyboardMapper() {
        final ButtonMapper<Integer> buttonMapper = new ButtonMapper<>();
        buttonMapper.map(Button.DPAD_UP, Input.Keys.UP);
        buttonMapper.map(Button.DPAD_DOWN, Input.Keys.DOWN);
        buttonMapper.map(Button.DPAD_LEFT, Input.Keys.LEFT);
        buttonMapper.map(Button.DPAD_RIGHT, Input.Keys.RIGHT);

        buttonMapper.map(Button.START, Input.Keys.ENTER);
        buttonMapper.map(Button.SELECT, Input.Keys.SHIFT_RIGHT);

        buttonMapper.map(Button.A, Input.Keys.Z);
        buttonMapper.map(Button.B, Input.Keys.X);
        buttonMapper.map(Button.X, Input.Keys.A);
        buttonMapper.map(Button.Y, Input.Keys.S);

        buttonMapper.map(Button.L1, Input.Keys.Q);
        buttonMapper.map(Button.L2, Input.Keys.W);
        buttonMapper.map(Button.R1, Input.Keys.C);
        buttonMapper.map(Button.R2, Input.Keys.D);

        return buttonMapper;
    }

    public static ButtonMapper<Integer> defaultLogitechButtonMapper() {
        final ButtonMapper<Integer> buttonMapper = new ButtonMapper<>();
        // dpad buttons are read from axis's, not button codes directly
        // consider allowing axis's to be configured.

        buttonMapper.map(Button.START, 9);
        buttonMapper.map(Button.SELECT, 8);

        buttonMapper.map(Button.A, 1);
        buttonMapper.map(Button.B, 2);
        buttonMapper.map(Button.X, 0);
        buttonMapper.map(Button.Y, 3);

        // left joystick pressed 10
        // right joystick pressed 11

        buttonMapper.map(Button.L1, 4);
        buttonMapper.map(Button.L2, 6);
        buttonMapper.map(Button.R1, 5);
        buttonMapper.map(Button.R2, 7);

        return buttonMapper;
    }

}
```

Now lets start building our controller interface and implementations. Unfortunately this implementation does not directly support input combinations yet, but that will be supported in the near future.
```{.java .numberLines startFrom="1"}
public interface Controller {

    boolean isUp();
    boolean isDown();
    boolean isLeft();
    boolean isRight();

    boolean isStart();
    boolean isSelect();

    boolean isA();
    boolean isB();
    boolean isX();
    boolean isY();

    boolean isL1();
    boolean isL2();
    boolean isR1();
    boolean isR2();

    boolean isAny();

    long whenUp();
    long whenDown();
    long whenLeft();
    long whenRight();

    long whenStart();
    long whenSelect();

    long whenA();
    long whenB();
    long whenX();
    long whenY();

    long whenL1();
    long whenL2();
    long whenR1();
    long whenR2();

}
```

To support the "when" functions we will build an input time recorder class that will be updated on every keypress.
```{.java .numberLines startFrom="1"}
public class InputTimeRecorder {

    final public Map<Button, Long> lastPressedMap = new HashMap<>();

    public void recordUp() {
        record(Button.DPAD_UP);
    }

    public void recordDown() {
        record(Button.DPAD_DOWN);
    }

    public void recordLeft() {
        record(Button.DPAD_LEFT);
    }

    public void recordRight() {
        record(Button.DPAD_RIGHT);
    }

    public void recordStart() {
        record(Button.START);
    }

    public void recordSelect() {
        record(Button.SELECT);
    }

    public void recordA() {
        record(Button.A);
    }

    public void recordB() {
        record(Button.B);
    }

    public void recordX() {
        record(Button.X);
    }

    public void recordY() {
        record(Button.Y);
    }

    public void recordL1() {
        record(Button.L1);
    }

    public void recordL2() {
        record(Button.L2);
    }

    public void recordR1() {
        record(Button.R1);
    }

    public void recordR2() {
        record(Button.R2);
    }

    public long whenUp() {
        return when(Button.DPAD_UP);
    }

    public long whenDown() {
        return when(Button.DPAD_DOWN);
    }

    public long whenLeft() {
        return when(Button.DPAD_LEFT);
    }

    public long whenRight()  {
        return when(Button.DPAD_RIGHT);
    }

    public long whenStart()  {
        return when(Button.START);
    }

    public long whenSelect()  {
        return when(Button.SELECT);
    }

    public long whenA()  {
        return when(Button.A);
    }

    public long whenB()  {
        return when(Button.B);
    }

    public long whenX()  {
        return when(Button.X);
    }

    public long whenY()  {
        return when(Button.Y);
    }

    public long whenL1()  {
        return when(Button.L1);
    }

    public long whenL2()  {
        return when(Button.L2);
    }

    public long whenR1() {
        return when(Button.R1);
    }

    public long whenR2() {
        return when(Button.R2);
    }

    private void record(final Button button) {
        lastPressedMap.put(button, TimeUtils.millis());
    }

    private long when(final Button button) {
        if(lastPressedMap.containsKey(button)) { return lastPressedMap.get(button); }
        return 0L;
    }

}
```

Our keyboard input implementation
```{.java .numberLines startFrom="1"}
public class KeyboardController implements Controller {

    private final InputTimeRecorder inputTimeRecorder = new InputTimeRecorder();

    private final ButtonMapper<Integer> buttonMapper;

    public KeyboardController() {
        this(ButtonMapper.defaultKeyboardMapper());
    }

    public KeyboardController(final ButtonMapper<Integer> buttonMapper) {
        this.buttonMapper = buttonMapper;
    }

    @Override
    public boolean isUp() {
        final boolean pressed = Gdx.input.isKeyPressed(buttonMapper.get(Button.DPAD_UP));
        if(pressed) { inputTimeRecorder.recordUp(); }
        return pressed;
    }

    @Override
    public boolean isDown() {
        final boolean pressed = Gdx.input.isKeyPressed(buttonMapper.get(Button.DPAD_DOWN));
        if(pressed) { inputTimeRecorder.recordDown(); }
        return pressed;
    }

    @Override
    public boolean isLeft() {
        final boolean pressed = Gdx.input.isKeyPressed(buttonMapper.get(Button.DPAD_LEFT));
        if(pressed) { inputTimeRecorder.recordLeft(); }
        return pressed;
    }

    @Override
    public boolean isRight() {
        final boolean pressed = Gdx.input.isKeyPressed(buttonMapper.get(Button.DPAD_RIGHT));
        if(pressed) { inputTimeRecorder.recordUp(); }
        return pressed;
    }

    @Override
    public boolean isStart() {
        final boolean pressed = Gdx.input.isKeyPressed(buttonMapper.get(Button.START));
        if(pressed) { inputTimeRecorder.recordStart(); }
        return pressed;
    }

    @Override
    public boolean isSelect() {
        final boolean pressed =Gdx.input.isKeyPressed(buttonMapper.get(Button.SELECT));
        if(pressed) { inputTimeRecorder.recordSelect(); }
        return pressed;
    }

    @Override
    public boolean isA() {
        final boolean pressed = Gdx.input.isKeyPressed(buttonMapper.get(Button.A));
        if(pressed) { inputTimeRecorder.recordA(); }
        return pressed;
    }

    @Override
    public boolean isB() {
        final boolean pressed = Gdx.input.isKeyPressed(buttonMapper.get(Button.B));
        if(pressed) { inputTimeRecorder.recordB(); }
        return pressed;
    }

    @Override
    public boolean isX() {
        final boolean pressed = Gdx.input.isKeyPressed(buttonMapper.get(Button.X));
        if(pressed) { inputTimeRecorder.recordX(); }
        return pressed;
    }

    @Override
    public boolean isY() {
        final boolean pressed = Gdx.input.isKeyPressed(buttonMapper.get(Button.Y));
        if(pressed) { inputTimeRecorder.recordUp(); }
        return pressed;
    }

    @Override
    public boolean isL1() {
        final boolean pressed = Gdx.input.isKeyPressed(buttonMapper.get(Button.L1));
        if(pressed) { inputTimeRecorder.recordL1(); }
        return pressed;
    }

    @Override
    public boolean isL2() {
        final boolean pressed = Gdx.input.isKeyPressed(buttonMapper.get(Button.L2));
        if(pressed) { inputTimeRecorder.recordL2(); }
        return pressed;
    }

    @Override
    public boolean isR1() {
        final boolean pressed = Gdx.input.isKeyPressed(buttonMapper.get(Button.R1));
        if(pressed) { inputTimeRecorder.recordR1(); }
        return pressed;
    }

    @Override
    public boolean isR2() {
        final boolean pressed = Gdx.input.isKeyPressed(buttonMapper.get(Button.R2));
        if(pressed) { inputTimeRecorder.recordR2(); }
        return pressed;
    }

    @Override
    public boolean isAny() {
        return Gdx.input.isTouched() || Gdx.input.isKeyPressed(Input.Keys.ANY_KEY);
    }

    @Override
    public long whenUp() {
        return inputTimeRecorder.whenUp();
    }

    @Override
    public long whenDown() {
        return inputTimeRecorder.whenDown();
    }

    @Override
    public long whenLeft() {
        return inputTimeRecorder.whenLeft();
    }

    @Override
    public long whenRight() {
        return inputTimeRecorder.whenRight();
    }

    @Override
    public long whenStart() {
        return inputTimeRecorder.whenStart();
    }

    @Override
    public long whenSelect() {
        return inputTimeRecorder.whenSelect();
    }

    @Override
    public long whenA() {
        return inputTimeRecorder.whenA();
    }

    @Override
    public long whenB() {
        return inputTimeRecorder.whenB();
    }

    @Override
    public long whenX() {
        return inputTimeRecorder.whenX();
    }

    @Override
    public long whenY() {
        return inputTimeRecorder.whenY();
    }

    @Override
    public long whenL1() {
        return inputTimeRecorder.whenL1();
    }

    @Override
    public long whenL2() {
        return inputTimeRecorder.whenL2();
    }

    @Override
    public long whenR1() {
        return inputTimeRecorder.whenR1();
    }

    @Override
    public long whenR2() {
        return inputTimeRecorder.whenR2();
    }

}
```

Our logitech controller input implementation
```{.java .numberLines startFrom="1"}
public class LogitechController implements Controller {

    private final InputTimeRecorder inputTimeRecorder = new InputTimeRecorder();

    private com.badlogic.gdx.controllers.Controller controller;

    private final ButtonMapper<Integer> buttonMapper;

    public LogitechController() {
        this(0, ButtonMapper.defaultLogitechButtonMapper());
    }

    public LogitechController(final int controllerNumber, final ButtonMapper<Integer> buttonMapper) {
        this.buttonMapper = buttonMapper;

        if(Controllers.getControllers().size < (controllerNumber + 1)) { return; }
        controller = Controllers.getControllers().get(controllerNumber);
    }

    @Override
    public boolean isUp() {
        if(controller == null) { return false; }
        final boolean pressed = controller.getAxis(1) < -0.75;
        if(pressed) { inputTimeRecorder.recordUp(); }
        return pressed;
    }

    @Override
    public boolean isDown() {
        if(controller == null) { return false; }
        final boolean pressed = controller.getAxis(1) > 0.75;
        if(pressed) { inputTimeRecorder.recordDown(); }
        return pressed;
    }

    @Override
    public boolean isLeft() {
        if(controller == null) { return false; }
        final boolean pressed = controller.getAxis(0) < -0.75;
        if(pressed) { inputTimeRecorder.recordLeft(); }
        return pressed;
    }

    @Override
    public boolean isRight() {
        if(controller == null) { return false; }
        final boolean pressed = controller.getAxis(0) > 0.75;
        if(pressed) { inputTimeRecorder.recordUp(); }
        return pressed;
    }

    @Override
    public boolean isStart() {
        if(controller == null) { return false; }
        final boolean pressed = controller.getButton(buttonMapper.get(Button.START));
        if(pressed) { inputTimeRecorder.recordStart(); }
        return pressed;
    }

    @Override
    public boolean isSelect() {
        if(controller == null) { return false; }
        final boolean pressed = controller.getButton(buttonMapper.get(Button.SELECT));
        if(pressed) { inputTimeRecorder.recordSelect(); }
        return pressed;
    }

    @Override
    public boolean isA() {
        if(controller == null) { return false; }
        final boolean pressed = controller.getButton(buttonMapper.get(Button.A));
        if(pressed) { inputTimeRecorder.recordA(); }
        return pressed;
    }

    @Override
    public boolean isB() {
        if(controller == null) { return false; }
        final boolean pressed = controller.getButton(buttonMapper.get(Button.B));
        if(pressed) { inputTimeRecorder.recordB(); }
        return pressed;
    }

    @Override
    public boolean isX() {
        if(controller == null) { return false; }
        final boolean pressed = controller.getButton(buttonMapper.get(Button.X));
        if(pressed) { inputTimeRecorder.recordX(); }
        return pressed;
    }

    @Override
    public boolean isY() {
        if(controller == null) { return false; }
        final boolean pressed = controller.getButton(buttonMapper.get(Button.Y));
        if(pressed) { inputTimeRecorder.recordUp(); }
        return pressed;
    }

    @Override
    public boolean isL1() {
        if(controller == null) { return false; }
        final boolean pressed = controller.getButton(buttonMapper.get(Button.L1));
        if(pressed) { inputTimeRecorder.recordL1(); }
        return pressed;
    }

    @Override
    public boolean isL2() {
        if(controller == null) { return false; }
        final boolean pressed = controller.getButton(buttonMapper.get(Button.L2));
        if(pressed) { inputTimeRecorder.recordL2(); }
        return pressed;
    }

    @Override
    public boolean isR1() {
        if(controller == null) { return false; }
        final boolean pressed = controller.getButton(buttonMapper.get(Button.R1));
        if(pressed) { inputTimeRecorder.recordR1(); }
        return pressed;
    }

    @Override
    public boolean isR2() {
        if(controller == null) { return false; }
        final boolean pressed = controller.getButton(buttonMapper.get(Button.R2));
        if(pressed) { inputTimeRecorder.recordR2(); }
        return pressed;
    }

    @Override
    public boolean isAny() {
        return isUp() || isDown() || isLeft() || isRight()
                || isSelect() || isStart()
                || isL1() || isL2() || isR1() || isR2()
                || isA() || isB() || isX() || isY();
    }

    @Override
    public long whenUp() {
        return inputTimeRecorder.whenUp();
    }

    @Override
    public long whenDown() {
        return inputTimeRecorder.whenDown();
    }

    @Override
    public long whenLeft() {
        return inputTimeRecorder.whenLeft();
    }

    @Override
    public long whenRight() {
        return inputTimeRecorder.whenRight();
    }

    @Override
    public long whenStart() {
        return inputTimeRecorder.whenStart();
    }

    @Override
    public long whenSelect() {
        return inputTimeRecorder.whenSelect();
    }

    @Override
    public long whenA() {
        return inputTimeRecorder.whenA();
    }

    @Override
    public long whenB() {
        return inputTimeRecorder.whenB();
    }

    @Override
    public long whenX() {
        return inputTimeRecorder.whenX();
    }

    @Override
    public long whenY() {
        return inputTimeRecorder.whenY();
    }

    @Override
    public long whenL1() {
        return inputTimeRecorder.whenL1();
    }

    @Override
    public long whenL2() {
        return inputTimeRecorder.whenL2();
    }

    @Override
    public long whenR1() {
        return inputTimeRecorder.whenR1();
    }

    @Override
    public long whenR2() {
        return inputTimeRecorder.whenR2();
    }

}
```

Now lets bring it all together and wrap our input methods in a "multi" controller that is capable of simultaneously supporting configureable keyboard and logitech input without any hassle to either the programmer or the game player. Note how MultiController also implements the Controller interface just as  KeyboardController and LogitechController. 
```{.java .numberLines startFrom="1"}
public class MultiController implements Controller {

    private final Controller[] controllers;

    public MultiController(final Controller... controllers) {
        this.controllers = controllers;
    }


    @Override
    public boolean isUp() {
        for(Controller controller : controllers) {
            if(controller.isUp()) { return true; }
        }
        return false;
    }

    @Override
    public boolean isDown() {
        for(Controller controller : controllers) {
            if(controller.isDown()) { return true; }
        }
        return false;
    }

    @Override
    public boolean isLeft() {
        for(Controller controller : controllers) {
            if(controller.isLeft()) { return true; }
        }
        return false;
    }

    @Override
    public boolean isRight() {
        for(Controller controller : controllers) {
            if(controller.isRight()) { return true; }
        }
        return false;
    }

    @Override
    public boolean isStart() {
        for(Controller controller : controllers) {
            if(controller.isStart()) { return true; }
        }
        return false;
    }

    @Override
    public boolean isSelect() {
        for(Controller controller : controllers) {
            if(controller.isSelect()) { return true; }
        }
        return false;
    }

    @Override
    public boolean isA() {
        for(Controller controller : controllers) {
            if(controller.isA()) { return true; }
        }
        return false;
    }

    @Override
    public boolean isB() {
        for(Controller controller : controllers) {
            if(controller.isB()) { return true; }
        }
        return false;
    }

    @Override
    public boolean isX() {
        for(Controller controller : controllers) {
            if(controller.isX()) { return true; }
        }
        return false;
    }

    @Override
    public boolean isY() {
        for(Controller controller : controllers) {
            if(controller.isY()) { return true; }
        }
        return false;
    }

    @Override
    public boolean isL1() {
        for(Controller controller : controllers) {
            if(controller.isL1()) { return true; }
        }
        return false;
    }

    @Override
    public boolean isL2() {
        for(Controller controller : controllers) {
            if(controller.isL2()) { return true; }
        }
        return false;
    }

    @Override
    public boolean isR1() {
        for(Controller controller : controllers) {
            if(controller.isR1()) { return true; }
        }
        return false;
    }

    @Override
    public boolean isR2() {
        for(Controller controller : controllers) {
            if(controller.isR2()) { return true; }
        }
        return false;
    }

    @Override
    public boolean isAny() {
        for(Controller controller : controllers) {
            if(controller.isAny()) { return true; }
        }
        return false;
    }

    @Override
    public long whenUp() {
        for(Controller controller : controllers) {
            if(controller.whenUp() > 0) { return controller.whenUp(); }
        }
        return 0L;
    }

    @Override
    public long whenDown() {
        for(Controller controller : controllers) {
            if(controller.whenDown() > 0) {
                return controller.whenDown(); }
        }
        return 0L;
    }

    @Override
    public long whenLeft() {
        for(Controller controller : controllers) {
            if(controller.whenLeft() > 0) {
                return controller.whenLeft(); }
        }
        return 0L;
    }

    @Override
    public long whenRight() {
        for(Controller controller : controllers) {
            if(controller.whenRight() > 0) {
                return controller.whenRight(); }
        }
        return 0L;
    }

    @Override
    public long whenStart() {
        for(Controller controller : controllers) {
            if(controller.whenStart() > 0) {
                return controller.whenStart(); }
        }
        return 0L;
    }

    @Override
    public long whenSelect() {
        for(Controller controller : controllers) {
            if(controller.whenSelect() > 0) {
                return controller.whenSelect(); }
        }
        return 0L;
    }

    @Override
    public long whenA() {
        for(Controller controller : controllers) {
            if(controller.whenA() > 0) {
                return controller.whenA(); }
        }
        return 0L;
    }

    @Override
    public long whenB() {
        for(Controller controller : controllers) {
            if(controller.whenB() > 0) {
                return controller.whenB(); }
        }
        return 0L;
    }

    @Override
    public long whenX() {
        for(Controller controller : controllers) {
            if(controller.whenX() > 0) {
                return controller.whenX(); }
        }
        return 0L;
    }

    @Override
    public long whenY() {
        for(Controller controller : controllers) {
            if(controller.whenY() > 0) { return controller.whenY(); }
        }
        return 0L;
    }

    @Override
    public long whenL1() {
        for(Controller controller : controllers) {
            if(controller.whenL1() > 0) { return controller.whenL1(); }
        }
        return 0L;
    }

    @Override
    public long whenL2() {
        for(Controller controller : controllers) {
            if(controller.whenL2() > 0) { return controller.whenL2(); }
        }
        return 0L;
    }

    @Override
    public long whenR1() {
        for(Controller controller : controllers) {
            if(controller.whenR1() > 0) { return controller.whenR1(); }
        }
        return 0L;
    }

    @Override
    public long whenR2() {
        for(Controller controller : controllers) {
            if(controller.whenR2() > 0) { return controller.whenR2(); }
        }
        return 0L;
    }
}
```


Using MultiController in your game should now only require a minimum of code.

In the beginning of your game screen create a multi controller with the following code.
```{.java .numberLines startFrom="1"}
private Controller controller = new MultiController(new KeyboardController(), new LogitechController());
```

A sample handle input method for a space shooter.
```{.java .numberLines startFrom="1"}
private void handleInput() {
    final float deltaTime = Gdx.graphics.getDeltaTime();

    // process user input
    ship.vx = 0;
    ship.vy = 0;

    if (controller.isLeft()) {
        ship.vx = -deltaTime;
    }
    if (controller.isRight()) {
        ship.vx = deltaTime;
    }
    if (controller.isUp()) {
        ship.vy = deltaTime;
    }
    if (controller.isDown()) {
        ship.vy = -deltaTime;
    }
    if (controller.isL1()) {
        ship.weaponScrollLeft();
    }
    if (controller.isR1()) {
        ship.weaponScrollRight();
    }
    if (controller.isSelect()) {
        game.setScreen(new GameScreen(game));
    }
    float[] v = Vector.unit2d(ship.vx, ship.vy);
    ship.vx = v[0] * ship.speed;
    ship.vy = v[1] * ship.speed;
    ship.handle(this);

    if (controller.isA()) {
        ship.shoot(this);
    }
}
```

I hope this is better than manually referencing Gdx.input calls directly!
