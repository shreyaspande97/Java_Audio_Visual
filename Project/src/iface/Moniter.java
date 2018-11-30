package iface;

public class Moniter
{
	int n[] = new int[4];
	boolean valueSet=false;
	public synchronized int[] get()
	{
		while(!valueSet)
		{
			try{
				wait();
			}catch(InterruptedException e)
			{
				System.out.println("InterruptedException caught");
			}
		}
		valueSet=false;
		System.out.println("Got : "+this.n[0]+" "+this.n[1]+" "+this.n[2]+" "+this.n[3]);
		notify();
		return n;
	}
	
	public synchronized void put(int n, int s, int e, int v)
	{
		while(valueSet)
		{
			try{
				wait();
			}catch(InterruptedException ex)
			{
				System.out.println("InterruptedException caught");
			}
		}
		this.n[0] = n;
		this.n[1] = s;
		this.n[2] = e;
		this.n[3] = v;
		valueSet=true;
		System.out.println("put : "+this.n[0]+" "+this.n[1]+" "+this.n[2]+" "+this.n[3]);
		notify();
	}
}