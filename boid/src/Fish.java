import java.awt.geom.Point2D;

public class Fish {

	int     m_idx;

    double  m_x0, m_y0;      // 現在の位置(x,y)
    double  m_vx0, m_vy0;    // 現在の Speed
    
    double  m_x1,m_y1;      // 次ステップにおける位置(x,y) 
    double  m_vx1, m_vy1;   // 次ステップでの Speed 

	double  m_r_repulsive;  // 嫌悪範囲
	double  m_r_parallel;   // 平行動作範囲
	double  m_r_attraction; // 誘引範囲
	double  m_max_speed;    // 最大速度
	double  m_blind;        // 死角
	
	double pointwidth;
	
//	double r_hiritsu = 0.1;
//	double p_hiritsu = 0.23;
//	double m_hiritsu = 0.12;
	double r_hiritsu = 0.24;
	double p_hiritsu = 0.32;
	double m_hiritsu = 0.22;
	double motono_hiritsu = 0.77;
	
	/* コンストラクタ */
	public Fish(World world, int m_idx){
		
		m_max_speed = 40;
		
		m_x0 = Math.random() * world.width;
		m_y0 = Math.random() * world.height;
		double m_v = Math.random() * m_max_speed;
		double cos = MathUtil.pmnrandam();
		m_vy0 = m_v * cos;
		m_vx0 = m_v * Math.sqrt(1.0 - cos * cos) * MathUtil.pmrandam();
		m_vx1 = m_vx0;
		m_vy1 = m_vy0;
		m_r_repulsive = 35;
		m_r_parallel = 60;
		m_r_attraction = 90;
		m_blind = -0.5;
		
		this.m_idx = m_idx;
		
		pointwidth = 5.0;
		
	}
	
	
	public Point2D.Double getFishPoint(){
		return new Point2D.Double(m_x0, m_y0);
	}
	
	public boolean inView(Fish fish){
		return m_blind < MathUtil.getcos(m_vx0, m_vy0, fish.m_x0 - m_x0, fish.m_y0 - m_y0);
	}
	

	protected void decideNextSituation(World world){
		
		double rxsum = 0.0;
		double rysum = 0.0;
		double rdiv = 0.001;
		double pxsum = 0.0;
		double pysum = 0.0;
		double pdiv = 0.001;
		double mxsum = 0.0;
		double mysum = 0.0;
		double mdiv = 0.001;
		
		for(int i = 0; i < world.m_fish_kosuh; i++){
			Point2D.Double f1 = world.m_fish[m_idx].getFishPoint();
			Point2D.Double f2 = world.m_fish[i].getFishPoint();
			double distance = getFishPoint().distance(world.m_fish[i].getFishPoint());

			if(i != m_idx && inView(world.m_fish[i])){    // 視界にあること

				if(distance < m_r_repulsive){   // 嫌悪範囲
//					rxsum = rxsum - (world.m_fish[i].m_x0 - m_x0) / Math.pow(distance, 2.0);
//					rysum = rysum - (world.m_fish[i].m_y0 - m_y0) / Math.pow(distance, 2.0);
//					rdiv += 1 / Math.pow(distance, 2.0);

					rxsum = rxsum + (world.m_fish[i].m_x0 - m_x0) / distance;
					rysum = rysum + (world.m_fish[i].m_y0 - m_y0) / distance;
					rdiv += 1;

				}else if(distance < m_r_parallel){   //平行範囲
					pxsum = pxsum + world.m_fish[i].m_vx0;
					pysum = pysum + world.m_fish[i].m_vy0;
					pdiv ++;

				}else if(distance < m_r_attraction){    // 誘引範囲
//					mxsum = mxsum + (world.m_fish[i].m_x0 - m_x0) / Math.pow(distance, 2.0);
//					mysum = mysum + (world.m_fish[i].m_y0 - m_y0) / Math.pow(distance, 2.0);
//					mdiv ++;				

					mxsum = mxsum + (world.m_fish[i].m_x0 - m_x0) / distance;
					mysum = mysum + (world.m_fish[i].m_y0 - m_y0) / distance;
					mdiv ++;				

				}
			}	
		}
		double m_v = Math.random() * m_max_speed / 3.0;
		double cos = Math.random() * MathUtil.pmnrandam();
		m_vx1 = m_vx0 * motono_hiritsu - rxsum / rdiv * r_hiritsu + pxsum / pdiv * p_hiritsu + mxsum / mdiv * m_hiritsu + m_v * cos;
		m_vy1 = m_vy0 * motono_hiritsu - rysum / rdiv * r_hiritsu + pysum / pdiv * p_hiritsu + mysum / mdiv * m_hiritsu + m_v * Math.sqrt(1.0 - cos * cos) * MathUtil.pmrandam();
		
		
		if (MathUtil.getv_length(m_vx1, m_vy1) > m_max_speed) {
			m_vx1 = (m_vx1 / MathUtil.getv_length(m_vx1, m_vy1)) * m_max_speed;
			m_vy1 = (m_vy1 / MathUtil.getv_length(m_vx1, m_vy1)) * m_max_speed;
		}
		
		if (!Double.isNaN(m_vx1) && !Double.isNaN(m_vx1) && !Double.isInfinite(m_vx1) && !Double.isInfinite(m_vx1)
				&&!Double.isNaN(m_vy1) && !Double.isNaN(m_vy1) && !Double.isInfinite(m_vy1) && !Double.isInfinite(m_vy1)) {

			if (m_x0 + m_vx1 <= 0 || m_x0 + m_vx1 >= world.width) {
				m_vx1 = -(m_vx1);
			}
			m_x1 = m_x0 + m_vx1;

			if (m_y0 + m_vy1 <= 0 || m_y0 + m_vy1 >= world.height) {
				m_vy1 = -(m_vy1);
			}
			m_y1 = m_y0 + m_vy1;
		}else{
			m_vx1 = -(m_vx0);
			m_vy1 = -(m_vy0);
			m_x1 = m_x0 + m_vx1;
			m_y1 = m_y0 + m_vy1;
		}
		
		if(m_x1 < 0 || m_y1 < 0){
			System.out.print("");
		}
	}
	
	protected void changeToNextStation(World world){
		m_x0 = m_x1;
		m_y0 = m_y1;
		m_vx0 = m_vx1;
		m_vy0 = m_vy1;
	}
	

}
