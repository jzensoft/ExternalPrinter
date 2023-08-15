package com.smartfinder.printermanager.utils

enum class EpsonSeries(val value: Int) {
    TM_M10(0),
    TM_M30(1),
    TM_P20(2),
    TM_P60(3),
    TM_P60I(4),
    TM_P80(5),
    TM_T20(6),
    TM_T60(7),
    TM_T70(8),
    TM_T81(9),
    TM_T82(10),
    TM_T83(11),
    TM_T88(12),
    TM_T90(13),
    TM_T90KP(14),
    TM_U220(15),
    TM_U330(16),
    TM_L90(17),
    TM_H6000(18),
    TM_T83III(19),
    TM_T100(20),
    TM_M30II(22),
    TS_100(23),
    TM_M50(24),
    TM_T88VII(25),
    TM_L90LFC(26),
    EU_M30(27),
    TM_L100(28),
    TM_P20II(30),
    TM_P80II(31),
    TM_M30III(32),
    TM_M50II(33),
    TM_M55(34);

    companion object {
        fun getSeries(s: String): Int {
            return when (s.uppercase()) {
                TM_M10.name -> TM_M10.value
                TM_M30.name -> TM_M30.value
                TM_P20.name -> TM_P20.value
                TM_P60.name -> TM_P60.value
                TM_P60I.name -> TM_P60I.value
                TM_P80.name -> TM_P80.value
                TM_T20.name -> TM_T20.value
                TM_T60.name -> TM_T60.value
                TM_T70.name -> TM_T70.value
                TM_T81.name -> TM_T81.value
                TM_T82.name -> TM_T82.value
                TM_T83.name -> TM_T83.value
                TM_T90.name -> TM_T90.value
                TM_T90KP.name -> TM_T90KP.value
                TM_U220.name -> TM_U220.value
                TM_U330.name -> TM_U330.value
                TM_L90.name -> TM_L90.value
                TM_H6000.name -> TM_H6000.value
                TM_T83III.name -> TM_T83III.value
                TM_T100.name -> TM_T100.value
                TM_M30II.name -> TM_M30II.value
                TS_100.name -> TS_100.value
                TM_M50.name -> TM_M50.value
                TM_T88VII.name -> TM_T88VII.value
                TM_L90LFC.name -> TM_L90LFC.value
                EU_M30.name -> EU_M30.value
                TM_L100.name -> TM_L100.value
                TM_P20II.name -> TM_P20II.value
                TM_P80II.name -> TM_P80II.value
                TM_M30III.name -> TM_M30III.value
                TM_M50II.name -> TM_M50II.value
                TM_M55.name -> TM_M55.value
                else -> TM_T88.value
            }
        }
    }
}