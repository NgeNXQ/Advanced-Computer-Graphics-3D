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

public final class FixedOrientationControllerComponent extends Component {
    private static final float MOVEMENT_SPEED = 1.0f;

    private TransformComponent transform;

    private boolean isMovingLeft;
    private boolean isMovingRight;
    private boolean isMovingForward;
    private boolean isMovingBackward;
    private boolean isMovingUp;
    private boolean isMovingDown;

    private final Button buttonMoveLeft;
    private final Button buttonMoveRight;
    private final Button buttonMoveForward;
    private final Button buttonMoveBackward;
    private final Button buttonMoveUp;
    private final Button buttonMoveDown;

    private final Vector3 up = new Vector3(0.0f, 1.0f, 0.0f);
    private final Vector3 right = new Vector3(0.0f, 0.0f, 0.0f);
    private final Vector3 toOrigin = new Vector3(0.0f, 0.0f, 0.0f);
    private final Vector3 newToOrigin = new Vector3(0.0f, 0.0f, 0.0f);

    public FixedOrientationControllerComponent(Entity entity, Button buttonMoveForward, Button buttonMoveBackward, Button buttonMoveLeft, Button buttonMoveRight, Button buttonMoveUp, Button buttonMoveDown) {
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
        buttonMoveUp.setVisibility(View.INVISIBLE);
        buttonMoveDown.setVisibility(View.INVISIBLE);

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

        this.buttonMoveDown = buttonMoveDown;
        buttonMoveDown.setId(View.generateViewId());
        buttonMoveDown.setPadding(0, 0, 0, 0);
        buttonMoveDown.setText("↓");
        buttonMoveDown.setTextSize(textSize);
        LayoutParams paramsMoveDownRight = new LayoutParams(buttonSize, buttonSize);
        paramsMoveDownRight.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        paramsMoveDownRight.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        paramsMoveDownRight.rightMargin = rightOffset;
        paramsMoveDownRight.bottomMargin = bottomOffset;
        buttonMoveDown.setLayoutParams(paramsMoveDownRight);
        buttonMoveDown.setOnTouchListener(this::handleMoveDownButtonTouch);

        this.buttonMoveUp = buttonMoveUp;
        buttonMoveUp.setId(View.generateViewId());
        buttonMoveUp.setPadding(0, 0, 0, 0);
        buttonMoveUp.setText("↑");
        buttonMoveUp.setTextSize(textSize);
        LayoutParams paramsMoveUpRight = new LayoutParams(buttonSize, buttonSize);
        paramsMoveUpRight.addRule(RelativeLayout.ABOVE, buttonMoveDown.getId());
        paramsMoveUpRight.addRule(RelativeLayout.ALIGN_LEFT, buttonMoveDown.getId());
        paramsMoveUpRight.bottomMargin = spacing;
        buttonMoveUp.setLayoutParams(paramsMoveUpRight);
        buttonMoveUp.setOnTouchListener(this::handleMoveUpButtonTouch);

        Framework.getInstance().getViewport().register(buttonMoveLeft);
        Framework.getInstance().getViewport().register(buttonMoveRight);
        Framework.getInstance().getViewport().register(buttonMoveForward);
        Framework.getInstance().getViewport().register(buttonMoveBackward);
        Framework.getInstance().getViewport().register(buttonMoveUp);
        Framework.getInstance().getViewport().register(buttonMoveDown);
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

    private boolean handleMoveUpButtonTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.isMovingUp = true;
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                this.isMovingUp = false;
                return true;
            default:
                return false;
        }
    }

    private boolean handleMoveDownButtonTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.isMovingDown = true;
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                this.isMovingDown = false;
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
        this.buttonMoveUp.setVisibility(View.VISIBLE);
        this.buttonMoveDown.setVisibility(View.VISIBLE);

        this.transform = super.getEntity().getComponent(TransformComponent.class);
    }

    @Override
    public void onUpdate(float deltaTime) {
        Vector3 position = this.transform.getPosition();
        Vector3 rotation = this.transform.getRotation();
        float moveSpeed = FixedOrientationControllerComponent.MOVEMENT_SPEED * deltaTime;

        this.toOrigin.setXYZ(0, 0, 0);
        Vector3.subtract(this.toOrigin, position, this.toOrigin);
        Vector3.normalize(this.toOrigin, this.toOrigin);

        this.right.setXYZ(0, 0, 0);
        Vector3.cross(this.toOrigin, this.up,this.right);
        Vector3.normalize(this.right, this.right);

        if (this.isMovingForward) {
            position.setX(position.getX() + this.toOrigin.getX() * moveSpeed);
            position.setY(position.getY() + this.toOrigin.getY() * moveSpeed);
            position.setZ(position.getZ() + this.toOrigin.getZ() * moveSpeed);
        }

        if (this.isMovingBackward) {
            position.setX(position.getX() - this.toOrigin.getX() * moveSpeed);
            position.setY(position.getY() - this.toOrigin.getY() * moveSpeed);
            position.setZ(position.getZ() - this.toOrigin.getZ() * moveSpeed);
        }

        if (this.isMovingLeft) {
            position.setX(position.getX() - this.right.getX() * moveSpeed);
            position.setY(position.getY() - this.right.getY() * moveSpeed);
            position.setZ(position.getZ() - this.right.getZ() * moveSpeed);
        }

        if (this.isMovingRight) {
            position.setX(position.getX() + this.right.getX() * moveSpeed);
            position.setY(position.getY() + this.right.getY() * moveSpeed);
            position.setZ(position.getZ() + this.right.getZ() * moveSpeed);
        }

        if (this.isMovingUp) {
            position.setY(position.getY() + moveSpeed);
        }

        if (this.isMovingDown) {
            position.setY(position.getY() - moveSpeed);
        }

        this.newToOrigin.setXYZ(0, 0, 0);
        Vector3.subtract(this.newToOrigin, position, this.newToOrigin);
        Vector3.normalize(this.newToOrigin, this.newToOrigin);

        float yaw = (float) Math.toDegrees(Math.atan2(this.newToOrigin.getX(), this.newToOrigin.getZ()));
        float pitch = (float) Math.toDegrees(Math.asin(-this.newToOrigin.getY()));

        rotation.setX(pitch);
        rotation.setY(yaw);
        rotation.setZ(0);
    }

    @Override
    public void onDestroy() {
        this.buttonMoveLeft.setVisibility(View.INVISIBLE);
        this.buttonMoveRight.setVisibility(View.INVISIBLE);
        this.buttonMoveForward.setVisibility(View.INVISIBLE);
        this.buttonMoveBackward.setVisibility(View.INVISIBLE);
        this.buttonMoveUp.setVisibility(View.INVISIBLE);
        this.buttonMoveDown.setVisibility(View.INVISIBLE);
    }
}