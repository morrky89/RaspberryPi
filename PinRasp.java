import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.wiringpi.SoftPwm;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Raspberry Pi - Controlling two DC motors and 7-segment led display 
 *  
 * @author Marcin Wiercioch
 */

public class PinRasp {

	final static GpioController gpio = GpioFactory.getInstance();

	final static GpioPinDigitalOutput pin_a = gpio.provisionDigitalOutputPin(
			RaspiPin.GPIO_10, "A", PinState.HIGH);
	final static GpioPinDigitalOutput pin_b = gpio.provisionDigitalOutputPin(
			RaspiPin.GPIO_11, "B", PinState.HIGH);
	final static GpioPinDigitalOutput pin_c = gpio.provisionDigitalOutputPin(
			RaspiPin.GPIO_12, "C", PinState.HIGH);
	final static GpioPinDigitalOutput pin_d = gpio.provisionDigitalOutputPin(
			RaspiPin.GPIO_02, "D", PinState.HIGH);
	final static GpioPinDigitalOutput pin_e = gpio.provisionDigitalOutputPin(
			RaspiPin.GPIO_09, "E", PinState.HIGH);
	final static GpioPinDigitalOutput pin_f = gpio.provisionDigitalOutputPin(
			RaspiPin.GPIO_13, "F", PinState.HIGH);
	final static GpioPinDigitalOutput pin_g = gpio.provisionDigitalOutputPin(
			RaspiPin.GPIO_05, "G", PinState.HIGH);

	final static GpioPinDigitalOutput pin_1 = gpio.provisionDigitalOutputPin(
			RaspiPin.GPIO_08, "1", PinState.HIGH);
	final static GpioPinDigitalOutput pin_2 = gpio.provisionDigitalOutputPin(
			RaspiPin.GPIO_14, "2", PinState.HIGH);
	
	
	final static GpioPinDigitalOutput m1_1 = gpio.provisionDigitalOutputPin(
			RaspiPin.GPIO_07, "m11", PinState.LOW);
	final static GpioPinDigitalOutput m1_2 = gpio.provisionDigitalOutputPin(
			RaspiPin.GPIO_00, "m12", PinState.LOW);
	final static GpioPinDigitalOutput m2_1 = gpio.provisionDigitalOutputPin(
			RaspiPin.GPIO_03, "m21", PinState.LOW);
	final static GpioPinDigitalOutput m2_2 = gpio.provisionDigitalOutputPin(
			RaspiPin.GPIO_04, "m22", PinState.LOW);
	
	final static GpioPinDigitalOutput m1_e = gpio.provisionDigitalOutputPin(
			RaspiPin.GPIO_06, "m1e", PinState.LOW);
	final static GpioPinDigitalOutput m2_e = gpio.provisionDigitalOutputPin(
			RaspiPin.GPIO_01, "m2e", PinState.LOW);
	
	static void clockwise() {
		m1_1.high();
		m1_2.low();
	}
	
	static void counter_clockwise() {
		m1_1.low();
		m1_2.high();
	}
	
	static void clockwise2() {
		m2_1.high();
		m2_2.low();
	}
	
	static void counter_clockwise2() {
		m2_1.low();
		m2_2.high();
	}
	
	
	static void clear() {
		gpio.setState(PinState.HIGH, pin_a, pin_b, pin_c, pin_d, pin_e, pin_f,
				pin_g);
	}

	//methods for each number

	static void n1() {

		pin_b.low();
		pin_c.low();
	}

	static void n2() {

		pin_a.low();
		pin_b.low();
		pin_g.low();
		pin_e.low();
		pin_d.low();
	}

	static void n3() {

		pin_a.low();
		pin_b.low();
		pin_g.low();
		pin_c.low();
		pin_d.low();
	}

	static void n4() {

		pin_f.low();
		pin_g.low();
		pin_c.low();
		pin_b.low();
	}

	static void n5() {
		pin_a.low();
		pin_f.low();
		pin_g.low();
		pin_c.low();
		pin_d.low();
	}

	static void n6() {

		pin_a.low();
		pin_f.low();
		pin_g.low();
		pin_c.low();
		pin_d.low();
		pin_e.low();
	}

	static void n7() {

		pin_a.low();
		pin_b.low();
		pin_c.low();
	}

	static void n8() {

		pin_a.low();
		pin_b.low();
		pin_c.low();
		pin_d.low();
		pin_e.low();
		pin_f.low();
		pin_g.low();
	}

	static void n9() {

		pin_a.low();
		pin_b.low();
		pin_g.low();
		pin_f.low();
		pin_c.low();
		pin_d.low();
	}

	static void n0() {
		pin_a.low();
		pin_b.low();
		pin_c.low();
		pin_d.low();
		pin_e.low();
		pin_f.low();
	}

	public static void main(String[] args) throws InterruptedException {

		SoftPwm.softPwmCreate(6, 10, 100);
		SoftPwm.softPwmCreate(1, 10, 100);
		clockwise();
		counter_clockwise2();
		
		System.out.println("___Two DC motors and 7 segment led display controller___");
		System.out.println("Usage: two digit speed, 0 or 1 to specify clockwise or counter_clockwise rotation, 1 or 2 - motor number, for example 2101 will be 21 speed , 0 for rotate, 1 for first motor   ");
		clear();

		boolean running = true;
		int[] array = new int[4];
		array[0] = 0;
		array[1] = 0;

		while (running) {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						System.in));
				String line;

				line = in.readLine();
				if (line != null) {

					char[] result = line.toCharArray();

					if(result[0] == 'x') {
					clear();
	              			gpio.shutdown();
					System.exit(0);
					}

					//System.out.println(result[0] + " " + result[1]);
					array[0] = Integer.parseInt(String.valueOf(result[0]));
					array[1] = Integer.parseInt(String.valueOf(result[1]));
					array[2] = Integer.parseInt(String.valueOf(result[2]));
					array[3] = Integer.parseInt(String.valueOf(result[3]));
					int res = Integer.valueOf(String.valueOf(array[0]) + String.valueOf(array[1]));
					//System.out.println(res);
					if(array[3] == 1) {
					SoftPwm.softPwmWrite(1, res);
					if(array[2] == 0){
					clockwise();
					}else{
					counter_clockwise();}
					}else {
					if(array[3] == 2) {
					SoftPwm.softPwmWrite(6, res);
					if(array[2] == 1){
					clockwise2();
					}else{ counter_clockwise2();}
					}
					}
				}

				int l = 400;
				while (l-- > 0) {
					// left
					pin_1.low();
					pin_2.low();

					if (array[0] == 0) {
						n0();
					} else {
						if (array[0] == 1) {
							n1();
						} else {
							if (array[0] == 2) {
								n2();
							} else {
								if (array[0] == 3) {
									n3();
								} else {
									if (array[0] == 4) {
										n4();
									} else {
										if (array[0] == 5) {
											n5();
										} else {
											if (array[0] == 6) {
												n6();
											} else {
												if (array[0] == 7) {
													n7();
												} else {
													if (array[0] == 8) {
														n8();
													} else {
														if (array[0] == 9) {
															n9();
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
					Thread.sleep(5);
					clear();

					// right
					pin_1.high();
					pin_2.high();

					if (array[1] == 0) {
						n0();
					} else {
						if (array[1] == 1) {
							n1();
						} else {
							if (array[1] == 2) {
								n2();
							} else {
								if (array[1] == 3) {
									n3();
								} else {
									if (array[1] == 4) {
										n4();
									} else {
										if (array[1] == 5) {
											n5();
										} else {
											if (array[1] == 6) {
												n6();
											} else {
												if (array[1] == 7) {
													n7();
												} else {
													if (array[1] == 8) {
														n8();
													} else {
														if (array[1] == 9) {
															n9();
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}

					Thread.sleep(5);
					clear();

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		clear();
		gpio.shutdown();

	}

}
