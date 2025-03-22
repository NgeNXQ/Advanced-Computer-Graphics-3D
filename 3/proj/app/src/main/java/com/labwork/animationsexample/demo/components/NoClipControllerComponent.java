package com.labwork.animationsexample.demo.components;

import android.view.View;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.labwork.animationsexample.runtime.Framework;
import com.labwork.animationsexample.core.general.Entity;
import com.labwork.animationsexample.core.general.Vector3;
import com.labwork.animationsexample.core.components.common.Component;
import com.labwork.animationsexample.core.components.concrete.TransformComponent;

public final class NoClipControllerComponent extends Component {
    private static final float MOVEMENT_SPEED = 1.0f;
    private static final float ROTATION_SPEED = 45.0f;

    private TransformComponent transform;

    private boolean isMovingLeft;
    private boolean isMovingRight;
    private boolean isMovingForward;
    private boolean isMovingBackward;

    private boolean isRotatingUp;
    private boolean isRotatingDown;
    private boolean isRotatingLeft;
    private boolean isRotatingRight;

    private final Button buttonMoveLeft;
    private final Button buttonMoveRight;
    private final Button buttonMoveForward;
    private final Button buttonMoveBackward;

    private final Button buttonRotateUp;
    private final Button buttonRotateDown;
    private final Button buttonRotateLeft;
    private final Button buttonRotateRight;

    private final Vector3 tempVector = new Vector3(0, 0, 0);
    private final Vector3 moveDirection = new Vector3(0, 0, 0);

    public NoClipControllerComponent(Entity entity, Button buttonMoveForward, Button buttonMoveBackward, Button buttonMoveLeft, Button buttonMoveRight, Button buttonRotateUp, Button buttonRotateDown, Button buttonRotateLeft, Button buttonRotateRight) {
        super(entity);

        int spacing = 10;
        int leftOffset = 50;
        int rightOffset = 50;
        int buttonSize = 125;
        int bottomOffset = 150;
        float textSize = 30.0f;

        buttonMoveLeft.setVisibility(View.INVISIBLE);
        buttonMoveRight.setVisibility(View.INVISIBLE);
        buttonMoveForward.setVisibility(View.INVISIBLE);
        buttonMoveBackward.setVisibility(View.INVISIBLE);
        buttonRotateUp.setVisibility(View.INVISIBLE);
        buttonRotateDown.setVisibility(View.INVISIBLE);
        buttonRotateLeft.setVisibility(View.INVISIBLE);
        buttonRotateRight.setVisibility(View.INVISIBLE);

        this.buttonMoveLeft = buttonMoveLeft;
        buttonMoveLeft.setId(View.generateViewId());
        buttonMoveLeft.setPadding(0, 0, 0, 0);
        buttonMoveLeft.setText("←");
        buttonMoveLeft.setTextSize(textSize);
        LayoutParams paramsMoveLeft = new LayoutParams(buttonSize, buttonSize);
        paramsMoveLeft.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        paramsMoveLeft.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        paramsMoveLeft.leftMargin = leftOffset;
        paramsMoveLeft.bottomMargin = bottomOffset;
        buttonMoveLeft.setLayoutParams(paramsMoveLeft);
        buttonMoveLeft.setOnTouchListener(this::handleMoveLeftButtonTouch);

        this.buttonMoveBackward = buttonMoveBackward;
        buttonMoveBackward.setId(View.generateViewId());
        buttonMoveBackward.setPadding(0, 0, 0, 0);
        buttonMoveBackward.setText("↓");
        buttonMoveBackward.setTextSize(textSize);
        LayoutParams paramsMoveDown = new LayoutParams(buttonSize, buttonSize);
        paramsMoveDown.addRule(RelativeLayout.RIGHT_OF, buttonMoveLeft.getId());
        paramsMoveDown.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        paramsMoveDown.leftMargin = spacing;
        paramsMoveDown.bottomMargin = bottomOffset;
        buttonMoveBackward.setLayoutParams(paramsMoveDown);
        buttonMoveBackward.setOnTouchListener(this::handleMoveBackwardButtonTouch);

        this.buttonMoveForward = buttonMoveForward;
        buttonMoveForward.setId(View.generateViewId());
        buttonMoveForward.setPadding(0, 0, 0, 0);
        buttonMoveForward.setText("↑");
        buttonMoveForward.setTextSize(textSize);
        LayoutParams paramsMoveUp = new LayoutParams(buttonSize, buttonSize);
        paramsMoveUp.addRule(RelativeLayout.ABOVE, buttonMoveBackward.getId());
        paramsMoveUp.addRule(RelativeLayout.ALIGN_LEFT, buttonMoveBackward.getId());
        paramsMoveUp.bottomMargin = spacing;
        buttonMoveForward.setLayoutParams(paramsMoveUp);
        buttonMoveForward.setOnTouchListener(this::handleMoveForwardButtonTouch);

        this.buttonMoveRight = buttonMoveRight;
        buttonMoveRight.setId(View.generateViewId());
        buttonMoveRight.setPadding(0, 0, 0, 0);
        buttonMoveRight.setText("→");
        buttonMoveRight.setTextSize(textSize);
        LayoutParams paramsMoveRight = new LayoutParams(buttonSize, buttonSize);
        paramsMoveRight.addRule(RelativeLayout.RIGHT_OF, buttonMoveBackward.getId());
        paramsMoveRight.addRule(RelativeLayout.ALIGN_TOP, buttonMoveBackward.getId());
        paramsMoveRight.leftMargin = spacing;
        buttonMoveRight.setLayoutParams(paramsMoveRight);
        buttonMoveRight.setOnTouchListener(this::handleMoveRightButtonTouch);

        this.buttonRotateRight = buttonRotateRight;
        buttonRotateRight.setId(View.generateViewId());
        buttonRotateRight.setPadding(0, 0, 0, 0);
        buttonRotateRight.setText("→");
        buttonRotateRight.setTextSize(textSize);
        LayoutParams paramsRotateRight = new LayoutParams(buttonSize, buttonSize);
        paramsRotateRight.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        paramsRotateRight.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        paramsRotateRight.rightMargin = rightOffset;
        paramsRotateRight.bottomMargin = bottomOffset;
        buttonRotateRight.setLayoutParams(paramsRotateRight);
        buttonRotateRight.setOnTouchListener(this::handleRotateRightButtonTouch);

        this.buttonRotateDown = buttonRotateDown;
        buttonRotateDown.setId(View.generateViewId());
        buttonRotateDown.setPadding(0, 0, 0, 0);
        buttonRotateDown.setText("↓");
        buttonRotateDown.setTextSize(textSize);
        LayoutParams paramsRotateDown = new LayoutParams(buttonSize, buttonSize);
        paramsRotateDown.addRule(RelativeLayout.LEFT_OF, buttonRotateRight.getId());
        paramsRotateDown.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        paramsRotateDown.rightMargin = spacing;
        paramsRotateDown.bottomMargin = bottomOffset;
        buttonRotateDown.setLayoutParams(paramsRotateDown);
        buttonRotateDown.setOnTouchListener(this::handleRotateDownButtonTouch);

        this.buttonRotateLeft = buttonRotateLeft;
        buttonRotateLeft.setId(View.generateViewId());
        buttonRotateLeft.setPadding(0, 0, 0, 0);
        buttonRotateLeft.setText("←");
        buttonRotateLeft.setTextSize(textSize);
        LayoutParams paramsRotateLeft = new LayoutParams(buttonSize, buttonSize);
        paramsRotateLeft.addRule(RelativeLayout.LEFT_OF, buttonRotateDown.getId());
        paramsRotateLeft.addRule(RelativeLayout.ALIGN_TOP, buttonRotateDown.getId());
        paramsRotateLeft.rightMargin = spacing;
        buttonRotateLeft.setLayoutParams(paramsRotateLeft);
        buttonRotateLeft.setOnTouchListener(this::handleRotateLeftButtonTouch);

        this.buttonRotateUp = buttonRotateUp;
        buttonRotateUp.setId(View.generateViewId());
        buttonRotateUp.setPadding(0, 0, 0, 0);
        buttonRotateUp.setText("↑");
        buttonRotateUp.setTextSize(textSize);
        LayoutParams paramsRotateUp = new LayoutParams(buttonSize, buttonSize);
        paramsRotateUp.addRule(RelativeLayout.ABOVE, buttonRotateDown.getId());
        paramsRotateUp.addRule(RelativeLayout.ALIGN_LEFT, buttonRotateDown.getId());
        paramsRotateUp.bottomMargin = spacing;
        buttonRotateUp.setLayoutParams(paramsRotateUp);
        buttonRotateUp.setOnTouchListener(this::handleRotateUpButtonTouch);

        Framework.getInstance().getViewport().register(buttonMoveLeft);
        Framework.getInstance().getViewport().register(buttonMoveRight);
        Framework.getInstance().getViewport().register(buttonMoveForward);
        Framework.getInstance().getViewport().register(buttonMoveBackward);
        Framework.getInstance().getViewport().register(buttonRotateUp);
        Framework.getInstance().getViewport().register(buttonRotateDown);
        Framework.getInstance().getViewport().register(buttonRotateLeft);
        Framework.getInstance().getViewport().register(buttonRotateRight);
    }

    private boolean handleMoveForwardButtonTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.isMovingForward = true;
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                this.isMovingForward = false;
                return true;
            default:
                return false;
        }
    }

    private boolean handleMoveBackwardButtonTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.isMovingBackward = true;
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                this.isMovingBackward = false;
                return true;
            default:
                return false;
        }
    }

    private boolean handleMoveLeftButtonTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.isMovingLeft = true;
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                this.isMovingLeft = false;
                return true;
            default:
                return false;
        }
    }

    private boolean handleMoveRightButtonTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.isMovingRight = true;
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                this.isMovingRight = false;
                return true;
            default:
                return false;
        }
    }

    private boolean handleRotateUpButtonTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.isRotatingUp = true;
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                this.isRotatingUp = false;
                return true;
            default:
                return false;
        }
    }

    private boolean handleRotateDownButtonTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.isRotatingDown = true;
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                this.isRotatingDown = false;
                return true;
            default:
                return false;
        }
    }

    private boolean handleRotateLeftButtonTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.isRotatingLeft = true;
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                this.isRotatingLeft = false;
                return true;
            default:
                return false;
        }
    }

    private boolean handleRotateRightButtonTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.isRotatingRight = true;
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                this.isRotatingRight = false;
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onStart() {
        this.buttonMoveLeft.setVisibility(View.VISIBLE);
        this.buttonMoveRight.setVisibility(View.VISIBLE);
        this.buttonMoveForward.setVisibility(View.VISIBLE);
        this.buttonMoveBackward.setVisibility(View.VISIBLE);
        this.buttonRotateUp.setVisibility(View.VISIBLE);
        this.buttonRotateDown.setVisibility(View.VISIBLE);
        this.buttonRotateLeft.setVisibility(View.VISIBLE);
        this.buttonRotateRight.setVisibility(View.VISIBLE);

        this.transform = super.getEntity().getComponent(TransformComponent.class);
    }

    @Override
    public void onUpdate(float deltaTime) {
        Vector3 position = this.transform.getPosition();
        Vector3 rotation = this.transform.getRotation();
        float moveSpeed = NoClipControllerComponent.MOVEMENT_SPEED * deltaTime;
        float rotateSpeed = NoClipControllerComponent.ROTATION_SPEED * deltaTime;

        if (this.isRotatingUp) {
            rotation.setX(rotation.getX() - rotateSpeed);
        }
        if (this.isRotatingDown) {
            rotation.setX(rotation.getX() + rotateSpeed);
        }
        if (this.isRotatingLeft) {
            rotation.setY(rotation.getY() + rotateSpeed);
        }
        if (this.isRotatingRight) {
            rotation.setY(rotation.getY() - rotateSpeed);
        }

        float pitch = rotation.getX();

        if (pitch > 90.0f)
            pitch = 90.0f;

        if (pitch < -90.0f)
            pitch = -90.0f;

        rotation.setX(pitch);

        Vector3 forward = this.transform.getForward();
        Vector3 right = this.transform.getRight();

        this.moveDirection.setXYZ(0, 0, 0);

        if (this.isMovingForward) {
            Vector3.add(this.moveDirection, forward, this.moveDirection);
        }
        if (this.isMovingBackward) {
            Vector3.subtract(this.moveDirection, forward, this.moveDirection);
        }
        if (this.isMovingRight) {
            Vector3.subtract(this.moveDirection, right, this.moveDirection);
        }
        if (this.isMovingLeft) {
            Vector3.add(this.moveDirection, right, this.moveDirection);
        }

        if (this.moveDirection.magnitude() > 0) {
            Vector3.normalize(this.moveDirection, this.tempVector);
            Vector3.multiply(this.tempVector, moveSpeed, this.moveDirection);
            position.setX(position.getX() + this.moveDirection.getX());
            position.setY(position.getY() + this.moveDirection.getY());
            position.setZ(position.getZ() + this.moveDirection.getZ());
        }
    }

    @Override
    public void onDestroy() {
        this.buttonMoveLeft.setVisibility(View.INVISIBLE);
        this.buttonMoveRight.setVisibility(View.INVISIBLE);
        this.buttonMoveForward.setVisibility(View.INVISIBLE);
        this.buttonMoveBackward.setVisibility(View.INVISIBLE);
        this.buttonRotateUp.setVisibility(View.INVISIBLE);
        this.buttonRotateDown.setVisibility(View.INVISIBLE);
        this.buttonRotateLeft.setVisibility(View.INVISIBLE);
        this.buttonRotateRight.setVisibility(View.INVISIBLE);
    }
}