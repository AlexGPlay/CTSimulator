package yiplay.tc.cpu.register;

import java.util.logging.Logger;

import yiplay.tc.AbstractComponent;
import yiplay.tc.cpu.bus.InternalBus;
import yiplay.util.Translate;

public class StatusRegister extends AbstractRegister{
	
	private final static Logger logger = Logger.getLogger( StatusRegister.class.getName() );
	
	private int zf, cf, of, sf, intf;
	
	private static AbstractComponent instance;
	
	private StatusRegister() {
	}
	
	public static AbstractComponent getInstance() {
		if(instance == null)
			instance = new StatusRegister();
		return instance;
	}
	
	@Override
	public void setData(short data) {
		short z = (short) ((data & 0xF000) >> 12);
		short c = (short) ((data & 0x0F00) >> 8);
		short o = (short) ((data & 0x00F0) >> 4);
		short s = (short) (data & 0x000F);
		
		this.zf = z;
		this.cf = c;
		this.of = o;
		this.sf = s;
	}

	public int getZf() {
		return zf;
	}

	public int getCf() {
		return cf;
	}

	public int getOf() {
		return of;
	}

	public int getSf() {
		return sf;
	}

	public int getIntf() {
		return intf;
	}

	public void Sr_Ib() {
		String z = Translate.toBinaryString((short) zf,4);
		String c = Translate.toBinaryString((short) cf,4);
		String o = Translate.toBinaryString((short) of,4);
		String s = Translate.toBinaryString((short) sf,4);
		
		short zcos = 0;
		zcos |= Short.valueOf(z, 2) << 12;
		zcos |= Short.valueOf(c, 2) << 8;
		zcos |= Short.valueOf(o, 2) << 4;
		zcos |= Short.valueOf(s, 2);
		
		logger.info(String.format("Sr_Ib signal launched === SR -> %d%d%d%d -> IB\n",zf,cf,of,sf));
		((InternalBus) InternalBus.getInstance()).setData(zcos);
	}
	
	public void Cli() {
		intf = 0;
	}
	
	public void Sti() {
		intf = 1;
	}

	public void reset() {
		zf = 0;
		cf = 0;
		of = 0;
		sf = 0;
		intf = 0;
	}

}
