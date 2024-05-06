package com.example.android.viewmodel;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.android.api.ApiService;
import com.example.android.bean.entity.WordDetail;

import java.util.List;

public class UserTestViewModel extends ViewModel {
    
    private ApiService apiService;
    //当前页码
    private MutableLiveData<Integer> currentPage = new MutableLiveData<>(0);
    //所有单词列表
    private MutableLiveData<List<WordDetail>>allWords = new MutableLiveData<>();
    //每页单词数量
    private static final int WORDS_PER_PAGE = 8;
    //用户认识的单词列表
    private MutableLiveData<List<WordDetail>> knownWords = new MutableLiveData<>();



















//    public UserTestViewModel(ApiService apiService){
//        this.apiService = apiService;
//        knownWords.setValue(new ArrayList<>());
//        fetchAllWords();
//    }
//    // 提供LiveData，返回当前页面的单词子列表，利用Transformations.map根据currentPage动态计算
//    //获取当前页面的单词列表
//    public LiveData<List<Word>> getCurrentPageWords() {
//        return Transformations.map(currentPage, page -> {
//            // 计算当前页起始和结束索引
//            int start = page * WORDS_PER_PAGE;
//            int end = Math.min(start + WORDS_PER_PAGE, allWords.getValue().size());
//            // 返回当前页的单词列表
//            return allWords.getValue().subList(start, end);
//        });
//    }
////    // 返回当前页的起始和结束单词位置,
////    public Pair<Integer, Integer> getCurrentPageRange() {
////        int cPage = currentPage.getValue();
////        int start = cPage * WORDS_PER_PAGE + 1; // 页面起始单词位置（1-indexed）
////        int end = Math.min((cPage + 1) * WORDS_PER_PAGE, allWords.getValue().size()); // 页面结束单词位置
////        return new Pair<>(start, end);
////    }
//
//    //标记单词是否被认识
//    public void markKnownWord(int position) {
//        // 获取当前页面的单词列表
//        List<Word> currentWords = getCurrentPageWords().getValue();
//        if (currentWords != null && position >= 0 && position < currentWords.size()){
//            Word word = currentWords.get(position);
//            word.setIs_known(!word.getIs_known());
//            // 更新已认识单词列表
//            // 创建一个新的列表，避免修改原列表
//            List<Word> originalKnownWords = knownWords.getValue();
//            List<Word> updatedWords = new ArrayList<>(originalKnownWords);
//            if (word.getIs_known()){
//                // 如果单词不在已认识单词列表中，则添加
//                if (!updatedWords.contains(word)){
//                    updatedWords.add(word);
//                }
//            }else{
//                updatedWords.remove(word);
//            }
//            // 更新已认识单词列表
//            knownWords.setValue(updatedWords);
//        }
//    }
//
//    // 加载下一页单词
//    public void nextPage() {
//        int nextPage = currentPage.getValue() + 1;
//        if (nextPage * WORDS_PER_PAGE <= allWords.getValue().size()) {
//            currentPage.setValue(nextPage);
//        }
//    }
//
//    //获取已认识的单词列表
//    public LiveData<List<Word>> getKnownWords() {
//        return knownWords;
//    }
//
//    // 从API获取单词列表
//    private void fetchAllWords() {
//        //apiService.getWords()...
//    }
}
