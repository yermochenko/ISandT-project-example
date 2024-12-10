package by.vsu.ist.web;

import jakarta.servlet.http.HttpServletRequest;

public class SumRequestParser {
	public static long parse(HttpServletRequest req) {
		long sumRubles = Long.parseLong(req.getParameter("sum-rubles"));
		if(sumRubles < 0) throw new IllegalArgumentException();
		long sumKopecks = Long.parseLong(req.getParameter("sum-kopecks"));
		if(sumKopecks < 0 || sumKopecks >= 100) throw new IllegalArgumentException();
		return sumRubles * 100 + sumKopecks;
	}
}
