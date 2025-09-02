package kr.co.iei.animal.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.animal.model.dao.AnimalDao;
import kr.co.iei.animal.model.vo.Animal;
import kr.co.iei.animal.model.vo.AnimalListData;
import kr.co.iei.member.model.vo.MemberListData;

@Service
public class AnimalService {
	@Autowired
	private AnimalDao animalDao;
	
	public Animal selectAnimalNo(int animalNo) {
		Animal animal = animalDao.selectAnimalNo(animalNo);
		return animal;
	}
	@Transactional
	public int insertAnimal(Animal animal) {
		int result = animalDao.insertAnimal(animal);
		return result;
	}
	public int searchAnimalNo(int memberNo) {
		int animalNo = animalDao.searchAnimalNo(memberNo);
		return animalNo;
	}
	@Transactional
	public int updateAnimal(Animal animal) {
		int result = animalDao.updateAnimal(animal);
		return result;
	}
	@Transactional
	public int admissionCheck(int animalNo) {
		int result = animalDao.admissionCheck(animalNo);
		return result;
	}
	public AnimalListData selectAnimalList(int animalPage) {
		int numPerPage = 10;
		
		int end = animalPage * numPerPage;
		int start = (end-numPerPage)+1;
		HashMap<String, Object> param = new HashMap<String,Object>();
		param.put("start", start);
		param.put("end", end);
		
		int totalCount = animalDao.selectAnimalCount();
		
		int totalPage = totalCount / numPerPage;
		if(totalCount % numPerPage != 0) {
			totalPage += 1;
		}
		
		int pageNaviSize = 5;
		
		int pageNo = ((animalPage-1)/pageNaviSize)*pageNaviSize+1;
		
		String pageNaviAni = "<ul class='pagination circle-style'>";
		if(pageNo != 1) {
			pageNaviAni += "<li>";
			pageNaviAni += "<a class='page-item' href='/admin/adminPageAni?animalPage="+(pageNo-1)+"'>";
			pageNaviAni += "<span class='material-icons'>chevron_left</span>";
			pageNaviAni += "</a>";
			pageNaviAni += "</li>";
		}
		for(int i=0;i<pageNaviSize;i++) {
			pageNaviAni += "<li>";
			if(pageNo == animalPage) {
				pageNaviAni += "<a class='page-item active-page' href='/admin/adminPageAni?animalPage="+pageNo+"'>";
			}else {
				pageNaviAni += "<a class='page-item' href='/admin/adminPageAni?animalPage="+pageNo+"'>";
			}
			pageNaviAni += pageNo;
			pageNaviAni += "</a>";
			pageNaviAni += "</li>";
			
			pageNo++;
			if(pageNo > totalPage) {
				break;
			}
		}
		if(pageNo <= totalPage) {
			pageNaviAni += "<li>";
			pageNaviAni += "<a class='page-item' href='/admin/adminPageAni?animalPage="+pageNo+"'>";
			pageNaviAni += "<span class='material-icons'>chevron_right</span>";
			pageNaviAni += "</a>";
			pageNaviAni += "</li>";
		}
		pageNaviAni += "</ul>";
		
		List list = animalDao.selectAnimalList(param);
		
		AnimalListData ald = new AnimalListData(list, pageNaviAni);
		return ald;
		
	}
	@Transactional
	public int changeAdmission(Animal a) {
		int result = animalDao.changeAdmission(a);
		return result;
	}
	@Transactional
	public boolean checkedChangeAdmission(String no, String admission, String inocul, String neuter) {
		StringTokenizer sT1 = new StringTokenizer(no, "/");
		StringTokenizer sT2 = new StringTokenizer(admission, "/");
		StringTokenizer sT3 = new StringTokenizer(inocul, "/");
		StringTokenizer sT4 = new StringTokenizer(neuter, "/");
		int result = 0;
		int count = sT1.countTokens();
		
		while(sT1.hasMoreTokens()) {
			String stringNo = sT1.nextToken();
			int animalNo = Integer.parseInt(stringNo);
			String stringAdmission = sT2.nextToken();
			int animalAdmission = Integer.parseInt(stringAdmission);
			String stringInocul = sT3.nextToken();
			String stringNeuter = sT4.nextToken();
			
			Animal a = new Animal();
			a.setAnimalNo(animalNo);
			a.setAnimalAdmission(animalAdmission);
			a.setAnimalInocul(stringInocul);
			a.setAnimalNeuter(stringNeuter);
			System.out.println(a);
			result += animalDao.changeAdmission(a);
		}
		return result == count;
	}
	public AnimalListData searchAnimalName(String animalName, int animalPage) {
		int numPerPage = 10;
		
		int end = animalPage * numPerPage;
		int start = (end-numPerPage)+1;
		HashMap<String, Object> param = new HashMap<String,Object>();
		param.put("start", start);
		param.put("end", end);
		
		int totalCount = animalDao.searchAnimalNameCount(animalName);
		
		int totalPage = totalCount / numPerPage;
		if(totalCount % numPerPage != 0) {
			totalPage += 1;
		
			int pageNaviSize = 5;
			
			int pageNo = ((animalPage-1)/pageNaviSize)*pageNaviSize+1;
			
			String pageNaviAni = "<ul class='pagination circle-style'>";
			if(pageNo != 1) {
				pageNaviAni += "<li>";
				pageNaviAni += "<a class='page-item' href='/admin/adminPageAni?animalName="+animalName+"&animalPage="+(pageNo-1)+"'>";
				pageNaviAni += "<span class='material-icons'>chevron_left</span>";
				pageNaviAni += "</a>";
				pageNaviAni += "</li>";
			}
			for(int i=0;i<pageNaviSize;i++) {
				pageNaviAni += "<li>";
				if(pageNo == animalPage) {
					pageNaviAni += "<a class='page-item active-page' href='/admin/adminPageAni?animalName="+animalName+"&animalPage="+pageNo+"'>";
				}else {
					pageNaviAni += "<a class='page-item' href='/admin/adminPageAni?animalName="+animalName+"&animalPage="+pageNo+"'>";
				}
				pageNaviAni += pageNo;
				pageNaviAni += "</a>";
				pageNaviAni += "</li>";
				
				pageNo++;
				if(pageNo > totalPage) {
					break;
				}
			}
			if(pageNo <= totalPage) {
				pageNaviAni += "<li>";
				pageNaviAni += "<a class='page-item' href='/admin/adminPageAni?animalName="+animalName+"&animalPage="+pageNo+"'>";
				pageNaviAni += "<span class='material-icons'>chevron_right</span>";
				pageNaviAni += "</a>";
				pageNaviAni += "</li>";
			}
			pageNaviAni += "</ul>";
			param.put("animalName", animalName);
			List list = animalDao.searchAnimalName(param);
			
			AnimalListData ald = new AnimalListData(list, pageNaviAni);
			return ald;
		}else {
			return null;
		}
	}
}
