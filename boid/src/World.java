public class World {
	
	int    m_fish_kosuh;  // 魚の個数
	Fish[] m_fish;      // 魚のリスト
	int height = 600;
	int width = 1200;
	
	/* コンストラクタ */
	public World(){
		m_fish_kosuh = 1000;
		m_fish = new Fish[m_fish_kosuh];
		for(int i = 0; i < m_fish_kosuh; i++){
			m_fish[i] = new Fish(this, i);
		}
		
	}
	
	public void changeSituation(){
		
		for(int i = 0; i < m_fish_kosuh; i++){
			m_fish[i].decideNextSituation(this);;
		}

		for(int i = 0; i < m_fish_kosuh; i++){
			m_fish[i].changeToNextStation(this);
		}
	}
	
	

}
