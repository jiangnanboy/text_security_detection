package com.sy.word_seg;

import com.github.houbb.segment.bs.SegmentBs;
import com.github.houbb.segment.support.tagging.pos.tag.impl.SegmentPosTaggings;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sy
 * @date 2024/1/7 15:51
 */
public class WordSegment {
    public static List<Entry> textSegment(String text) {
        var resultList = SegmentBs.newInstance()
                .posTagging(SegmentPosTaggings.simple())
                .segment(text);
        var entryList = resultList.stream().map(result -> new Entry(result.word(), result.pos())).collect(Collectors.toList());
        return entryList;
    }
}
