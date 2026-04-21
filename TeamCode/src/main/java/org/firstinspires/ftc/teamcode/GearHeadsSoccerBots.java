package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Basic Teleop", group = "Iterative Opmode")
public class BasicTeleop extends OpMode {

    // Define your motors
    private DcMotor leftMotor;
    private DcMotor rightMotor;

    @Override
    public void init() {
        // Initialize the motors
        leftMotor = hardwareMap.get(DcMotor.class, "left_motor");
        rightMotor = hardwareMap.get(DcMotor.class, "right_motor");

        // Set the direction of the motors
        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {
        // Get the gamepad input
        float leftY = -gamepad1.left_stick_y; // Negative because the motors are mounted backwards
        float rightY = -gamepad1.right_stick_y; // Negative because the motors are mounted backwards

        // Set the motor powers
        leftMotor.setPower(leftY);
        rightMotor.setPower(rightY);

        // Print the motor powers to the log
        telemetry.addData("Left Power", leftY);
        telemetry.addData("Right Power", rightY);
        telemetry.update();
    }
}
