package com.smartfinder.printermanager.utils

import com.epson.epos2.printer.Printer
import com.epson.epos2.printer.PrinterStatusInfo

class EpsonErrorMsg {
    companion object {
        fun makeErrorMsg(status: PrinterStatusInfo?): String {
            var msg = ""
            if (status == null) return msg
            if (status.online == Printer.FALSE) {
                msg += "Printer is offline."
            }
            if (status.connection == Printer.FALSE) {
                msg += "Please check the connection of the printer and the mobile terminal. Connection get lost."
            }
            if (status.coverOpen == Printer.TRUE) {
                msg += "Please close roll paper cover."
            }
            if (status.paper == Printer.PAPER_EMPTY) {
                msg += "Please check roll paper."
            }
            if (status.paperFeed == Printer.TRUE || status.panelSwitch == Printer.SWITCH_ON) {
                msg += "Please release a paper feed switch."
            }
            if (status.errorStatus == Printer.MECHANICAL_ERR || status.errorStatus == Printer.AUTOCUTTER_ERR) {
                msg += "Please remove jammed paper and close roll paper cover. Remove any jammed paper or foreign substances in the printer, and then turn the printer off and turn the printer on again"
                msg += "Then, If the printer doesn't recover from error, please cycle the power switch."
            }
            if (status.errorStatus == Printer.UNRECOVER_ERR) {
                msg =
                    "Please cycle the power switch of the printer. If same errors occurred even power cycled, the printer may out of order"
            }
            if (status.errorStatus == Printer.AUTORECOVER_ERR) {
                if (status.autoRecoverError == Printer.HEAD_OVERHEAT) {
                    msg += "Please wait until error LED of the printer turns off."
                    msg += "Print head of printer is hot."
                }
                if (status.autoRecoverError == Printer.MOTOR_OVERHEAT) {
                    msg += "Please wait until error LED of the printer turns off."
                    msg += "Motor Driver IC of printer is hot."
                }
                if (status.autoRecoverError == Printer.BATTERY_OVERHEAT) {
                    msg += "Please wait until error LED of the printer turns off."
                    msg += "Battery of printer is hot."
                }
                if (status.autoRecoverError == Printer.WRONG_PAPER) {
                    msg += "Please set correct roll paper."
                }
            }
            if (status.batteryLevel == Printer.BATTERY_LEVEL_0) {
                msg += "Please connect AC adapter or change the battery. Battery of printer is almost empty"
            }
            if (status.removalWaiting == Printer.REMOVAL_WAIT_PAPER) {
                msg += "Please remove paper"
            }
            if (status.unrecoverError == Printer.HIGH_VOLTAGE_ERR ||
                status.unrecoverError == Printer.LOW_VOLTAGE_ERR
            ) {
                msg += "Please check the voltage status."
            }
            return msg
        }

        fun dispPrinterWarnings(status: PrinterStatusInfo?): String {
            if (status == null) return ""
            var warningsMsg = ""
            if (status.paper == Printer.PAPER_NEAR_END) {
                warningsMsg += "Roll paper is nearly end"
            }
            if (status.batteryLevel == Printer.BATTERY_LEVEL_1) {
                warningsMsg += "Battery level of printer is low"
            }
            if (status.paperTakenSensor == Printer.REMOVAL_DETECT_PAPER) {
                warningsMsg += "Please take the receipt."
            }
            if (status.paperTakenSensor == Printer.REMOVAL_DETECT_UNKNOWN) {
                warningsMsg += "Please check if not ambient light reaches paper outlet and if paper taken sensor status in the printer is enable if you need to use paper taken sensor status."
            }
            return warningsMsg
        }
    }
}