package com.makeus.makeushackathon.src.posting;

import com.makeus.makeushackathon.config.BaseException;
import com.makeus.makeushackathon.src.comment.CommentRepository;
import com.makeus.makeushackathon.src.posting.dto.*;
import com.makeus.makeushackathon.src.tag.Tag;
import com.makeus.makeushackathon.src.tag.TagRepository;
import com.makeus.makeushackathon.src.user.User;
import com.makeus.makeushackathon.src.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.makeus.makeushackathon.config.BaseResponseStatus.*;
import static org.springframework.core.OrderComparator.sort;

@Service
@RequiredArgsConstructor
public class PostingService {
    private final PostingRepository postingRepository;
    private final TagRepository tagRepository;
    private final UserService userService;
    private final CommentRepository commentRepository;

    @Transactional
    public void postPosting(int userIdx, PostPostingReq postPostingReq) throws BaseException {
        User user = userService.retrieveUserInfoByUserIdx(userIdx);
        Posting posting = new Posting(postPostingReq.getPostingDescription(),postPostingReq.getPostingEmoji(),postPostingReq.getPostingThumbnailUrl(),
                postPostingReq.getPostingPicture1Url(),postPostingReq.getGetPostingPicture2Url(),user);
        try{
            postingRepository.save(posting);
        }catch (Exception exception){
            throw new BaseException(FAILED_TO_POST_POSTING);
        }
        List<String> tagList = postPostingReq.getTagNameList();
        for(int i=0;i<tagList.size();i++){
            String tagName = tagList.get(i);
            Tag tag  = new Tag(user,posting,tagName);
            try{
                tagRepository.save(tag);
            }catch (Exception exception){
                throw new BaseException(FAILED_TO_POST_TAG);
            }
        }
    }
    @Transactional(readOnly = true)
    public List<GetPostingsRes> getPostings() throws BaseException{

        List<GetPostingsRes> getPostingsResList = new ArrayList<>();

        List<Posting> postings = postingRepository.findAllByStatusOrderByPostingIdxDesc("ACTIVE");
        for(int i=0;i<postings.size();i++){
            Posting posting = postings.get(i);
            int postingIdx = posting.getPostingIdx();
            String postingDescription = posting.getPostingDescription();
            String postingThumbnailUrl=posting.getPostingThumbnailUrl();
            String postingPicture1Url=posting.getPostingPicture1Url();
            String postingPicture2Url=posting.getPostingPicture2Url();
            String createdDayBefore = "00일전";//todo 수정 필요
            //String postingEmoji = posting.getPostingEmoji();
            int commentCount = commentRepository.countAllByPostingAndStatus(posting,"ACTIVE");
            List<Tag> tagList = tagRepository.findAllByPostingAndStatus(posting,"ACTIVE");
            List<String> tagNameList = new ArrayList<>();
            for(int j=0;j<tagList.size();j++){
                String tagName = tagList.get(j).getTagName();
                tagNameList.add(tagName);
            }
            GetPostingsRes getPostingsRes = new GetPostingsRes(postingIdx,postingDescription,postingThumbnailUrl,postingPicture1Url,postingPicture2Url,
                    tagNameList,createdDayBefore,commentCount);
            getPostingsResList.add(getPostingsRes);
        }
        return getPostingsResList;
    }
    @Transactional(readOnly = true)
    public GetCalendarRes getCalendar(int userIdx, String year, String month)throws BaseException{
        User user = userService.retrieveUserInfoByUserIdx(userIdx);
        List<Posting> postingList = postingRepository.findAllByStatusAndUser("ACTIVE",user);
        List<GetCalendarDto> getCalendarDtoList = new ArrayList<>();
        List<GetMyPostingsRes> getMyPostingsResList = new ArrayList<>();


        for(int i=0;i<postingList.size();i++){
            Date createdAt = postingList.get(i).getCreatedAt();
            Calendar cal = Calendar.getInstance();
            cal.setTime(createdAt);
            int yearOf = cal.get(Calendar.YEAR);
            int monthOf = cal.get(Calendar.MONTH)+1;



            if(Integer.parseInt(year)==yearOf && Integer.parseInt(month)==monthOf){
                int day = cal.get(Calendar.DATE);
                String postingEmoji = postingList.get(i).getPostingEmoji();
                GetCalendarDto getCalendarDto = new GetCalendarDto(day,postingEmoji);


                int postingIdx = postingList.get(i).getPostingIdx();
                String postingDescription = postingList.get(i).getPostingDescription();
                String postingThumbnailUrl = postingList.get(i).getPostingThumbnailUrl();
                int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
                String korDayOfWeek="";
                switch (dayOfWeek){
                    case 1 :
                        korDayOfWeek = "일요일";
                        break;
                    case 2:
                        korDayOfWeek = "월요일";
                        break;
                    case 3 :
                        korDayOfWeek = "화요일";
                        break;
                    case 4:
                        korDayOfWeek = "수요일";
                        break;
                    case 5 :
                        korDayOfWeek = "목요일";
                        break;
                    case 6:
                        korDayOfWeek = "금요일";
                        break;
                    case 7 :
                        korDayOfWeek = "토요일";
                        break;
                }
                String postingDate = (Integer.toString(day))+"일 "+korDayOfWeek;
                GetMyPostingsRes getMyPostingsRes = new GetMyPostingsRes(postingIdx,postingDate,postingThumbnailUrl,postingDescription);

                getCalendarDtoList.add(getCalendarDto);
                getMyPostingsResList.add(getMyPostingsRes);
            }
        }
        Collections.sort(getMyPostingsResList,new CompareGetMyPostingsRes());
        GetCalendarRes getCalendarRes = new GetCalendarRes(getCalendarDtoList,getMyPostingsResList);
        return getCalendarRes;
    }

//    @Transactional(readOnly = true)
//    public List<GetMyPostingsRes> getMyPosting(int userIdx,String year,String month,String day) throws BaseException{
//        User user = userService.retrieveUserInfoByUserIdx(userIdx);
//
//        List<Posting> postingList = postingRepository.findAllByStatusAndUser("ACTIVE",user);
//        for(int i=0;i<postingList.size();i++){
//
//        }
//
//
//    }

//    @Transactional(readOnly = true)
//    public GetPostingRes getPosting(int postingIdx) throws BaseException{
//        Posting posting = postingRepository.findAllByPostingIdxAndStatus(postingIdx,"ACTIVE");
//        if(posting==null){
//            throw new BaseException(FAILED_TO_GET_POSTING);
//        }
//        String postingDescription = posting.getPostingDescription();
//        List<Tag> tagList = tagRepository.findAllByPostingAndStatus(posting,"ACTIVE");
//        List<String> tagNameList = new ArrayList<>();
//        for(int j=0;j<tagList.size();j++){
//            String tagName = tagList.get(j).getTagName();
//            tagNameList.add(tagName);
//        }
//    }
}
class CompareGetMyPostingsRes implements Comparator<GetMyPostingsRes>{
    @Override
    public int compare(GetMyPostingsRes o1, GetMyPostingsRes o2) {
        return o2.getPostingIdx()<o1.getPostingIdx() ? -1 : o2.getPostingIdx()>o1.getPostingIdx() ? 1:0;
    }

}

