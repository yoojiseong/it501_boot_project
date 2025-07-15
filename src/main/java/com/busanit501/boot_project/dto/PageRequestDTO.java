package com.busanit501.boot_project.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.net.URLEncoder;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

    @Builder.Default
    private int page =1;

    @Builder.Default
    private int size = 10;

    private String type; // 검색 종류 : t, c , w, tc , twc
    private String keyword;
    private String link;

    public String[] getTypes(){
        if(type == null || type.isEmpty()){
            return null;
        }
        return type.split(""); // twc => t,w,c로 바꿔줌
    }

    //페이징 정보, 1) 현재 페이지 2) 사이즈 3) 정렬 조건, 내림차순
    // ...props : 가변인자를 뜻함(제목 or 번호)아무거나 올 수 있음
    public Pageable getPageable(String...props){
        return PageRequest.of(this.page-1, this.size, Sort.by(props).descending());
    }

    public String getLink(){
        if(link == null || link.isEmpty()){
            StringBuilder builder = new StringBuilder();
            builder.append("page=" + this.page);
            builder.append("&size=" + this.size);

            if(type != null && !type.isEmpty()){
                builder.append("&type=" + type);
            }
            if(keyword != null && !keyword.isEmpty()){
                try{
                    builder.append("&keyword=" + URLEncoder.encode(keyword, "UTF-8"));
                }catch (Exception e){

                }
            }
            link = builder.toString();
        }
        return link;
    }
}
