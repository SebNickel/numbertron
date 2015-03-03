package main.java.iitb.neo.training.ds;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the "n" nodes Each nodes stores the absolute value of the number,
 * and a list of Z nodes to which it is attached. Every node is assumed to have
 * a value at the moment.
 * 
 * @author aman
 * 
 */
public class Number {

	public Number(String num, List<Integer> list) {
		this.zs_linked = new ArrayList<Integer>(list);
//		NumberFormat nf = NumberFormat.getInstance();
//		String formatted = nf.format(num);
//		this.value = Double.parseDouble(formatted);
		this.svalue = num;
		this.value = 1.0;
		
	}

	public Number() {
		// TODO Auto-generated constructor stub
	}

	public ArrayList<Integer> zs_linked;
	public Double value;
	public String svalue;

	public void serialize(OutputStream os) throws IOException {
		DataOutputStream dos = new DataOutputStream(os);
		dos.writeDouble(this.value);
		dos.writeUTF(svalue);
		dos.writeInt(this.zs_linked.size());
		for (int i = 0; i < this.zs_linked.size(); i++) {
			dos.writeInt(this.zs_linked.get(i));
		}
	}

	public void deserialize(InputStream is) throws IOException {
		DataInputStream dis = new DataInputStream(is);
		this.value = dis.readDouble();
		this.svalue = dis.readUTF();
		int numZConnected = dis.readInt();
		zs_linked = new ArrayList<Integer>();
		for (int i = 0; i < numZConnected; i++) {
			zs_linked.add(dis.readInt());
		}
	}

	@Override
	public String toString() {
		return "Number [zs_linked=" + zs_linked + ", svalue=" + svalue + "]";
	}

}
