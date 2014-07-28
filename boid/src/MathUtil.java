public class MathUtil {
	
	/* 単位ベクトルのx座標を返す */
	public static double getvx_u(double x, double y){
		return x / Math.sqrt(x * x + y * y);
		
	}

	/* 単位ベクトルのy座標を返す */
	public static double getvy_u(double x, double y){
		return y / Math.sqrt(x * x + y * y);
		
	}
	
	/* ベクトルの長さを返す */
	public static double getv_length(double x, double y){
		return Math.sqrt(x * x + y * y);
		
	}
	
	/* 2ベクトルがなす角度のコサイン値を返す */
	public static double getcos(double ax, double ay, double bx, double by){
		return (ax * bx + ay * by) / getv_length(ax, ay) * getv_length(bx, by);
		
	}
	/* プラス１〜マイナス１の乱数を渡す */
	public static double pmnrandam(){
		return (Math.random() - 0.5) * 2 ;
		
	}

	/* プラス１かマイナス１のどちらかの乱数を渡す */
	public static double pmrandam(){
		
		if(Math.random() - 0.5 < 0){
			return -1.0;
		}else{
			return 1.0;
		}
		
		
	}

	
}
