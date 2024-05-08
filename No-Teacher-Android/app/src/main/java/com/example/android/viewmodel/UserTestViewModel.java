package com.example.android.viewmodel;


import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.android.api.ApiService;
import com.example.android.bean.entity.WordDetail;
import com.example.android.util.TokenManager;

import java.util.Arrays;
import java.util.List;

public class UserTestViewModel extends ViewModel {

    private MutableLiveData<List<String>> wordsLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> testCompleteLiveData = new MutableLiveData<>();

    private int curPage = 0;
    private final int totalPages = 11; //11页单词

    public MutableLiveData<List<String>> getWordsLiveData() {
        return wordsLiveData;
    }

    public MutableLiveData<Boolean> getTestCompleteLiveData() {
        return testCompleteLiveData;
    }

    //获取当前页码
    public int getCurrentPage() {
        return curPage;
    }

    /**
     * @param
     * @return void
     * @author Lee
     * @description 调用网络API或从SharedPreferences获取单词
     * @date 2024/5/8 8:27
     */
    public void fetchWords(Context context) {
        if (curPage >= totalPages) {
            testCompleteLiveData.setValue(true);  // 发送测试完成的信号
        } else {
            // 模拟或从SharedPreferences加载数据
            List<String> words;
            if (curPage == 0) {
                words = TokenManager.loadServerWordsFromSharedPreferences(context);
            } else {
                // 模拟获取新的单词数据
                words = Arrays.asList("Word" + curPage + "1", "Word" + curPage + "2", "Word" + curPage + "3", "Word" + curPage + "4",
                        "Word" + curPage + "5", "Word" + curPage + "6", "Word" + curPage + "7", "Word" + curPage + "8");
            }
            wordsLiveData.postValue(words);
            curPage++;  // 确保在获取数据后递增页面
        }
    }

    public void resetTest(Context context) {
        curPage = 0;
        fetchWords(context);
    }

























    
//    private ApiService apiService;
//    //当前页码
//    private MutableLiveData<Integer> currentPage = new MutableLiveData<>(0);
//    //所有单词列表
//    private MutableLiveData<List<WordDetail>>allWords = new MutableLiveData<>();
//    //每页单词数量
//    private static final int WORDS_PER_PAGE = 8;
//    //用户认识的单词列表
//    private MutableLiveData<List<WordDetail>> knownWords = new MutableLiveData<>();


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
