/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="ParkAutoLeft")
public class ParkAutoLeft extends LinearOpMode {

    public Servo iArmL, iArmR;
    public double iArmLExtendPosition, iArmRExtendPosition, iArmLRetractPosition, iArmRRetractPosition;

    public DcMotor fr, fl, br, bl;
    public double MECANUM_DRIVE_SPEED, MECANUM_DRIVE_TURN_SPEED;

    public Servo oClaw;
    public double oClawClosePosition, oClawOpenPosition;

    @Override
    public void runOpMode() {

        iArmL = hardwareMap.get(Servo.class, "L1");
        iArmR = hardwareMap.get(Servo.class, "R1");
        iArmLExtendPosition = 0.08;
        iArmRExtendPosition = 0.92;
        iArmLRetractPosition = 0.96;
        iArmRRetractPosition = 0.04;

        fr = hardwareMap.get(DcMotor.class, "fr");
        fl = hardwareMap.get(DcMotor.class, "fl");
        br = hardwareMap.get(DcMotor.class, "br");
        bl = hardwareMap.get(DcMotor.class, "bl");

        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);

        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        fr.setPower(0);
        fl.setPower(0);
        br.setPower(0);
        bl.setPower(0);

        MECANUM_DRIVE_SPEED = 0.5;
        MECANUM_DRIVE_TURN_SPEED = 0.7;

        oClaw = hardwareMap.get(Servo.class, "oClaw");
        oClawClosePosition = 0.16;
        oClawOpenPosition = 0.035;

        // Wait for the game to start (driver presses START)
        waitForStart();

        moveForward();
        strafeLeft();

        retractIntakeArm();
        closeOuttakeClaw();

    }



    public void moveForward() {
        fr.setPower(0.1);
        fl.setPower(0.1);
        br.setPower(0.1);
        bl.setPower(0.1);
        sleep(700);
        fr.setPower(0);
        fl.setPower(0);
        br.setPower(0);
        bl.setPower(0);
    }

    public void strafeLeft() {
        fr.setPower(0.5);
        fl.setPower(-0.5);
        br.setPower(-0.5);
        bl.setPower(0.5);
        sleep(1200);
        fr.setPower(0);
        fl.setPower(0);
        br.setPower(0);
        bl.setPower(0);
    }

    public void retractIntakeArm()
    {
        iArmL.setPosition(iArmLRetractPosition);
        iArmR.setPosition(iArmRRetractPosition);
    }

    public void closeOuttakeClaw()
    {
        oClaw.setPosition(oClawClosePosition);
    }

}
