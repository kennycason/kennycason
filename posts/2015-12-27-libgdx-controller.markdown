---
title: LibGDX Game Controller 
author: Kenny Cason
tags: java, libgdx, game
---

As with any low level code, eventually you get tired of working with it and wrap up common behavior. In my case I wanted to design for a few things:

1. A native interface for simultaneously handling both keyboard and logitech controller input. 
2. A button mapper to easily allow for custom mappings.
3. Record time when buttons are pressed to allow for easier support for capturing button combinations.

### Get Library

GDX-Controller is now hosted in Maven Central here:
```xml
<dependency>
    <groupId>com.kennycason</groupId>
    <artifactId>gdx-controller</artifactId>
    <version>1.0</version>
</dependency>
```

The GitHub repository will be maintained <a href="https://github.com/kennycason/gdx-controller">here</a>.


### Implementation

We'll start with our basic definition of buttons. This could be expanded depending on needs. Given my love of classic SNES style games, this is sufficient.

A general interface for button enum classes
```{.java .numberLines startFrom="1"}
public interface Controls {
}
```

A simple SNES-like control schema (with L2,R2)
```{.java .numberLines startFrom="1"}
public enum GameControls implements Controls {
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

Now that we have our Button "interface" defined, we need to create mappings to the internal integer key codes and joystick axi. The reason this is important is because different input method's keys may map to different values. The key codes for a logitech controller is significantly different than a computer's keyboard key codes. This abstraction also allows a user to easily map keys differently, for example, inverting up and down, or jump and run.

The class for handling button mappings
```{.java .numberLines startFrom="1"}
public class ButtonMapper<V extends Controls> {

    private final ObjectMap<V, Integer> mapping = new ObjectMap<>();

    public ButtonMapper() {}

    public void map(V control, Integer mapping) {
        this.mapping.put(control, mapping);
    }

    public final Integer get(V control) {
        return mapping.get(control, -1);
    }

}
```

The class for handling axis mappings
```{.java .numberLines startFrom="1"}
public class AxisMapper<V> {

    protected final ObjectMap<V, Axis> mapping = new ObjectMap<>();

    public AxisMapper() {}

    public void map(V control, Axis mapping) {
        this.mapping.put(control, mapping);
    }

    public Axis get(V control) {
        return mapping.get(control, null);
    }

}
```

Now lets start building our controller interface and implementations. Unfortunately this implementation does not directly support input combinations yet, but that will be supported in the near future.
```{.java .numberLines startFrom="1"}
public abstract class Controller<V extends Controls> {

    private ObjectMap<V, Long> lastPressed = new ObjectMap<>();

    protected ButtonMapper<V> buttonMapper;

    protected AxisMapper<V> axisMapper;

    public abstract boolean isPressed(V control);

    public long when(V control) {
        return lastPressed.get(control, 0L);
    }

    public void record(V control) {
        lastPressed.put(control, TimeUtils.millis());
    }

}
```

Our keyboard input implementation
```{.java .numberLines startFrom="1"}
public class KeyboardController<V extends Controls> extends Controller<V> {

    public KeyboardController(final ButtonMapper<V> buttonMapper) {
        super.buttonMapper = buttonMapper;
    }

    @Override
    public boolean isPressed(V control) {
        final boolean pressed = Gdx.input.isKeyPressed(buttonMapper.get(control));
        if (pressed) { record(control); }
        return pressed;
    }

}
```

Our Logitech controller input implementation. It is very similar to the keyboard implementation with the exception of the handling of it's joystick.
```{.java .numberLines startFrom="1"}
public class LogitechController<V extends Controls> extends Controller<V> {

    private final com.badlogic.gdx.controllers.Controller controller;

    public LogitechController(final int controllerNumber,
                              final ButtonMapper<V> buttonMapper,
                              final AxisMapper axisMapper) {
        super.buttonMapper = buttonMapper;
        super.axisMapper = axisMapper;

        if(Controllers.getControllers().size < (controllerNumber + 1)) {
            controller = null;
            return;
        }
        controller = Controllers.getControllers().get(controllerNumber);
    }

    @Override
    public boolean isPressed(V control) {
        if (controller == null) { return false; }

        boolean pressed = false;
        if (axisMapper.get(control) != null) {
            final Axis axis = this.axisMapper.get(control);
            if (axis.threshold < 0) {
                pressed = controller.getAxis(axis.id) < axis.threshold;
            }
            if (axis.threshold > 0) {
                pressed = controller.getAxis(axis.id) > axis.threshold;
            }
        }
        else {
            pressed = controller.getButton(buttonMapper.get(control));
        }

        if (pressed) { record(control); }
        return pressed;
    }
}
```

Now lets bring it all together and wrap our input methods in a "multiplex" controller that is capable of simultaneously supporting configureable keyboard and logitech input without any hassle to either the programmer or the game player. Note how MultiController also implements the Controller interface just as  KeyboardController and LogitechController. 
```{.java .numberLines startFrom="1"}
public class MultiplexedController<V extends Controls> extends Controller<V> {

    private final Controller[] controllers;

    public MultiplexedController(final Controller... controllers) {
        this.controllers = controllers;
    }

    public boolean isPressed(final V control) {
        for (Controller controller : controllers) {
            if (controller.isPressed(control)) {
                return true;
            }
        }
        return false;
    }

    public long whenPressed(final V control) {
        for (Controller controller : controllers) {
            final long when = controller.when(control);
            if (when > 0) {
                return when;
            }
        }
        return 0L;
    }

}
```

Using MultiController in your game should now only require a minimum of code. You must first configure your controller, and then you can use it however you please. Below we will create a sample controller factory.
```{.java .numberLines startFrom="1"}
public class MyControllerFactory {

    public static MultiplexedController<GameControls> buildMultiController() {
        return new MultiplexedController<>(buildKeyboard(), buildLogitech());
    }

    public static KeyboardController<GameControls> buildKeyboard() {
        final ButtonMapper<GameControls> buttonMapper = new ButtonMapper<>();
        buttonMapper.map(GameControls.DPAD_UP, Input.Keys.UP);
        buttonMapper.map(GameControls.DPAD_DOWN, Input.Keys.DOWN);
        buttonMapper.map(GameControls.DPAD_LEFT, Input.Keys.LEFT);
        buttonMapper.map(GameControls.DPAD_RIGHT, Input.Keys.RIGHT);

        buttonMapper.map(GameControls.START, Input.Keys.ENTER);
        buttonMapper.map(GameControls.SELECT, Input.Keys.SHIFT_RIGHT);

        buttonMapper.map(GameControls.A, Input.Keys.Z);
        buttonMapper.map(GameControls.B, Input.Keys.X);
        buttonMapper.map(GameControls.X, Input.Keys.A);
        buttonMapper.map(GameControls.Y, Input.Keys.S);

        buttonMapper.map(GameControls.L1, Input.Keys.Q);
        buttonMapper.map(GameControls.L2, Input.Keys.W);
        buttonMapper.map(GameControls.R1, Input.Keys.C);
        buttonMapper.map(GameControls.R2, Input.Keys.D);

        return new KeyboardController<>(buttonMapper);
    }

    public static LogitechController<GameControls> buildLogitech() {
        final ButtonMapper<GameControls> buttonMapper = new ButtonMapper<>();

        buttonMapper.map(GameControls.START, 9);
        buttonMapper.map(GameControls.SELECT, 8);

        buttonMapper.map(GameControls.A, 1);
        buttonMapper.map(GameControls.B, 2);
        buttonMapper.map(GameControls.X, 0);
        buttonMapper.map(GameControls.Y, 3);

        buttonMapper.map(GameControls.L1, 4);
        buttonMapper.map(GameControls.L2, 6);
        buttonMapper.map(GameControls.R1, 5);
        buttonMapper.map(GameControls.R2, 7);

        final AxisMapper<GameControls> axisMapper = new AxisMapper<>();
        axisMapper.map(GameControls.DPAD_UP, new Axis(1, -0.75f));
        axisMapper.map(GameControls.DPAD_DOWN, new Axis(1, 0.75f));
        axisMapper.map(GameControls.DPAD_LEFT, new Axis(0, -0.75f));
        axisMapper.map(GameControls.DPAD_RIGHT, new Axis(0, 0.75f));

        return new LogitechController<>(0, buttonMapper, axisMapper);
    }

}
```

In the beginning of your game screen create a multi controller with the following code.
```{.java .numberLines startFrom="1"}
private Controller<GameControls> controller = MyControllerFactory.buildMultiController();
```

A sample handle input method for a space shooter.
```{.java .numberLines startFrom="1"}
private void handleInput(final float deltaTime) {
    // process user input
    ship.vx = 0;
    ship.vy = 0;

    if (controller.isPressed(GameControls.DPAD_LEFT)) {
        ship.vx = -deltaTime;
    }
    if (controller.isPressed(GameControls.DPAD_RIGHT)) {
        ship.vx = deltaTime;
    }
    if (controller.isPressed(GameControls.DPAD_UP)) {
        ship.vy = deltaTime;
    }
    if (controller.isPressed(GameControls.DPAD_DOWN)) {
        ship.vy = -deltaTime;
    }
    if (controller.isPressed(GameControls.L1)) {
        ship.weaponScrollLeft();
    }
    if (controller.isPressed(GameControls.R1)) {
        ship.weaponScrollRight();
    }
    if (controller.isPressed(GameControls.SELECT)) {
        game.setScreen(new GameScreen(game));
    }
    float[] v = Vector.unit2d(ship.vx, ship.vy);
    ship.vx = v[0] * ship.speed;
    ship.vy = v[1] * ship.speed;
    ship.handle(this);

    if (controller.isPressed(GameControls.A)) {
        ship.shoot(this);
    }
}
```

I hope this is better than manually referencing Gdx.input calls directly!
