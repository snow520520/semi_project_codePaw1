package kr.co.iei.protect.model.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.animal.model.vo.Animal;
import kr.co.iei.member.model.vo.Member;
import kr.co.iei.protect.model.dao.ProtectDao;
import kr.co.iei.protect.model.vo.Protect;

@Service
public class ProtectService {

    @Autowired
    private ProtectDao protectDao;

    @Transactional
    public int insertProtect(Protect ap, int memberNo, Animal animal) {
        List<Animal> matchingAnimals = protectDao.selectAnimalByNameAndAgeList(animal);
        if (matchingAnimals.isEmpty()) return -2;

        Animal selectedAnimal = matchingAnimals.get(0);
        List<Integer> existingProtects = protectDao.selectAnimalNoListByNo(selectedAnimal.getAnimalNo());

        if (!existingProtects.isEmpty()) return -1; 

        int nextProtectNo = protectDao.getNextProtectNo();
        ap.setProtectNo(nextProtectNo);
        ap.setMemberNo(memberNo);
        ap.setAnimalNo(selectedAnimal.getAnimalNo());

        return protectDao.insertProtect(ap);
    }

    public List<Protect> selectPageList(int currentPage, int recordCountPerPage) {
        int start = (currentPage - 1) * recordCountPerPage + 1;
        int end = currentPage * recordCountPerPage;
        List<Protect> list = protectDao.selectPageProtect(start, end);
        for (Protect ap : list) {
            if ("2".equals(ap.getProtectStatus())) ap.setThumbnailUrl("/image/complete.png");
            else ap.setThumbnailUrl(extractFirstImageSrc(ap.getProtectContent()));
        }
        return list;
    }

    private String extractFirstImageSrc(String content) {
        if (content == null) return "/image/slider/3.jpg";
        Pattern pattern = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) return matcher.group(1);
        else return "/image/slider/3.jpg";
    }

    public int getTotalCount() {
        return protectDao.selectTotalCount();
    }

    public String getPageNavi(int currentPage, int totalCount, int recordCountPerPage, int naviCountPerPage, String url) {
        int totalPage = (int) Math.ceil((double) totalCount / recordCountPerPage);
        int startNavi = ((currentPage - 1) / naviCountPerPage) * naviCountPerPage + 1;
        int endNavi = startNavi + naviCountPerPage - 1;
        if(endNavi > totalPage) endNavi = totalPage;
        boolean needPrev = startNavi != 1;
        boolean needNext = endNavi != totalPage;

        StringBuilder sb = new StringBuilder();
        sb.append("<ul class='pagination circle-style'>");
        if(needPrev) sb.append("<li><a class='page-item' href='" + url + "?page=" + (startNavi - 1) + "'><span class='material-icons'>chevron_left</span></a></li>");
        for(int i = startNavi; i <= endNavi; i++) {
            if(i == currentPage) sb.append("<li><a class='page-item active-page' href='" + url + "?page=" + i + "'>" + i + "</a></li>");
            else sb.append("<li><a class='page-item' href='" + url + "?page=" + i + "'>" + i + "</a></li>");
        }
        if(needNext) sb.append("<li><a class='page-item' href='" + url + "?page=" + (endNavi + 1) + "'><span class='material-icons'>chevron_right</span></a></li>");
        sb.append("</ul>");
        return sb.toString();
    }

    public List<Protect> selectPageListRead(int start, int end) {
        List<Protect> list = protectDao.selectPageProtect(start, end);
        for (Protect ap : list) {
            if ("2".equals(ap.getProtectStatus())) ap.setThumbnailUrl("/image/complete.png");
            else ap.setThumbnailUrl(extractFirstImageSrc(ap.getProtectContent()));
        }
        return list;
    }

    public Protect selectOneProtect(int protectNo) {
        Protect ap = protectDao.selectOneProtect(protectNo);
        if (ap != null && "2".equals(ap.getProtectStatus())) ap.setThumbnailUrl("/image/complete.png");
        return ap;
    }

    public Animal selectAnimal(int animalNo) {
        return protectDao.selectAnimal(animalNo);
    }

    public Member selectMember(int memberNo) {
        return protectDao.selectMember(memberNo);
    }

    @Transactional
    public int updateProtectContent(Protect ap) {
        return protectDao.updateProtectContent(ap);
    }
}
