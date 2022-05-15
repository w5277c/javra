# javra
Java AVR Assembler

!Проект в стадии разработки!

Здесь я практикуюсь в создании своего ассемблера для микроконтроллеров AVR на JavaSE8.

Я хочу создать некий аналог проекта https://github.com/Ro5bert/avra (взят за основу) на Java.
Планирую в дальнейшем использовать его для сборки своих прошивок на базе core5277.

Также я планирую в дальнейшем использовать данный опыт для создания(в рамках проекта core5277) на ассемблере эмулятора и ассемблера.
Одна из конечных целей - собрать прошивку AVR средствами самого AVR.


Пример вывода:

JAVRA Java AVR macro assembler Version 0.0.1
Licensed by GPL-3.0-or-later

WARNING! The project is not finished yet, it is under development.
!!!It is not recommended to use!!!

Warning[1] ./devices/atmega168.inc(18): unsupported #pragma
Warning[2] ./devices/atmega168.inc(19): unsupported #pragma
Warning[3] ./devices/atmega168.inc(20): unsupported #pragma
Warning[4] ./devices/atmega168.inc(21): unsupported #pragma
Message: ######## device atmega168
Message: included bldr secure
Message: included bldr bus5277
Warning[5] ./boot/bus5277_bldr.inc(47): err_cntr(r23) already assigned
Warning[6] ./boot/bus5277_bldr.inc(48): bus_addr(r25) already assigned
Message: ######## buffer offset:592
Message: ######## buffer size:116
Message: ######## available ram:503
Message: ######## drivers headers offset:392
Message: ######## tasks headers offset:488
Message: ######## free ram offset:708
Message: ######## stack end offset:1215
Message: ######## core freq:16
Message: ######## timers normal speed (100)
Message: ######## timers x2 speed (100)
Message: ######## timer c enabled
Message: included ram_copy
Message: included ram_copy16
Message: included ram_fill16
Message: included ram_copy_desc
Message: included ram_copy16_desc
Message: included bitnum_to_num
Message: ######## io baudrate:230400
Message: ######## logging disabled
Message: ######## dispatcher enabled
Message: ######## timers enabled
Message: included c5_timer_set
Message: included c5_timer_start
Message: included c5_timer_start_at
Message: included c5_timer_stop
Message: included driver hardware pcint v0.2
Message: included c5_ram_offset
Message: included c5_ram_realloc
Message: included ram_fill
Message: included port_mode_in
Message: included port_offsets
Message: included port_set_lo
Message: included port_set_hi
Message: included port_get
Message: included port_get_byte
Message: included driver dht11 v0.4
Message: included port_mode_out
Message: included driver ir v0.2
Message: included c5_eint_enable
Message: included c5_eint_disable
Message: included reg_bit_hi
Message: included reg_bit_lo
Message: included reg_bit_inv
Message: included driver hardware uart v0.6
Message: included c5_wait
Message: included c5_wait_2ms
Message: included driver buttons v0.4
Message: included c5_ram_extend
Message: included c5_timer_set_y
Message: included driver beeper v0.3
Message: included port_invert
Message: included driver bus5277 v0.4
Message: included crc8_maxim
Message: included crc8_block
Message: included rom_read_bytes
Message: included eeprom_read_bytes
Message: included eeprom_read_byte
Message: included eeprom_write_bytes
Message: included eeprom_write_byte
Message: included c5_uptime_copy
Message: included sdnf_sub
Message: included sdnf_add
Message: included sdnf_cp

--CODE-----------------------------------
 Start	= 0000, End = 0048, Length = 0049
 Start	= 1E00, End = 1FE2, Length = 01E3
 Start	= 0049, End = 16EB, Length = 16A3
 -----
 Total	:  6351 words (12702 bytes)

Build SUCCESS, warnings:6
(parsed: 7520 lines, total time: 0.44 s)
