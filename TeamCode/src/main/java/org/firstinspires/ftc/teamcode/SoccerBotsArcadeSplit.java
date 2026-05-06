package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "SoccerBots Arcade-Split", group = "Iterative Opmode")
public class SoccerBotsArcadeSplit extends OpMode {

    // Motors
    private DcMotor leftMotor;
    private DcMotor rightMotor;

    // Tuning constants
    private static final double DEADZONE = 0.05;
    private static final double SLOW_MODE_SCALE = 0.5;

    @Override
    public void init() {
        leftMotor = hardwareMap.get(DcMotor.class, "left_motor");
        rightMotor = hardwareMap.get(DcMotor.class, "right_motor");

        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {

        // Raw inputs
        double drive = -gamepad1.left_stick_y;
        double turn  = gamepad1.right_stick_x;

        // Apply deadzone
        drive = applyDeadzone(drive);
        turn  = applyDeadzone(turn);

        // Square inputs for finer control (preserve sign)
        drive = squareInput(drive);
        turn  = squareInput(turn);

        // Arcade drive math
        double leftPower  = drive + turn;
        double rightPower = drive - turn;

        // Normalize if values exceed 1.0
        double max = Math.max(Math.abs(leftPower), Math.abs(rightPower));
        if (max > 1.0) {
            leftPower /= max;
            rightPower /= max;
        }

        // Slow mode (right trigger)
        double slowFactor = 1.0 - (gamepad1.right_trigger * (1.0 - SLOW_MODE_SCALE));
        leftPower *= slowFactor;
        rightPower *= slowFactor;

        // Clip just to be safe
        leftPower  = Range.clip(leftPower, -1.0, 1.0);
        rightPower = Range.clip(rightPower, -1.0, 1.0);

        // Apply power
        leftMotor.setPower(leftPower);
        rightMotor.setPower(rightPower);

        // Telemetry
        telemetry.addData("Drive", drive);
        telemetry.addData("Turn", turn);
        telemetry.addData("Left Power", leftPower);
        telemetry.addData("Right Power", rightPower);
        telemetry.addData("Slow Mode", slowFactor);
        telemetry.update();
    }

    // Deadzone helper
    private double applyDeadzone(double value) {
        if (Math.abs(value) < DEADZONE) {
            return 0;
        }
        return value;
    }

    // Square input while preserving sign
    private double squareInput(double value) {
        return Math.signum(value) * value * value;
    }
}