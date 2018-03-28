package com.jfservice.common.bean.Jlt.other;

import java.util.List;

public class LbsInfoAlp {

	private Integer mcc;
	
	private Integer mnc;
	
	private List<Cell> cells;
	
	public Integer getMcc() {
		return mcc;
	}

	public void setMcc(Integer mcc) {
		this.mcc = mcc;
	}

	public Integer getMnc() {
		return mnc;
	}

	public void setMnc(Integer mnc) {
		this.mnc = mnc;
	}

	public List<Cell> getCells() {
		return cells;
	}

	public void setCells(List<Cell> cells) {
		this.cells = cells;
	}

	public static class Cell{
		private Integer c;
		
		private Integer l;
		
		private Integer s;
		
		public Integer getC() {
			return c;
		}

		public void setC(Integer c) {
			this.c = c;
		}

		public Integer getL() {
			return l;
		}

		public void setL(Integer l) {
			this.l = l;
		}

		public Integer getS() {
			return s;
		}

		public void setS(Integer s) {
			this.s = s;
		}

		public Integer getMc() {
			return mc;
		}

		public void setMc(Integer mc) {
			this.mc = mc;
		}

		public Integer getMn() {
			return mn;
		}

		public void setMn(Integer mn) {
			this.mn = mn;
		}

		private Integer mc;
		
		private Integer mn;
		
	}
}
