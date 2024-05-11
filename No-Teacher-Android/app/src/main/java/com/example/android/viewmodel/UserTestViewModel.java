package com.example.android.viewmodel;


import static com.example.android.constants.BuildConfig.WORD_SERVICE;
import static com.example.android.util.TokenManager.loadServerWordsFromSharedPreferences;
import static com.example.android.util.TokenManager.saveServerWordsToSharedPreferences;
import static com.example.android.util.TokenManager.saveWordsAndIds;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.android.api.ApiService;
import com.example.android.bean.entity.WordDetail;
import com.example.android.http.retrofit.BaseResponse;
import com.example.android.http.retrofit.RetrofitManager;
import com.example.android.util.GsonUtils;
import com.example.android.util.TokenManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserTestViewModel extends ViewModel {

    private MutableLiveData<List<String>> wordsLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> testCompleteLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> currentPageLiveData = new MutableLiveData<>();

    private int curPage = 0;
    private int totalPages = 1;//初始设置为-1，表示还未获取到总页数

    public MutableLiveData<Integer> getCurrentPageLiveData() {
        return currentPageLiveData;
    }

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
            List<String> words = null;

            requestTestWords(context, curPage);
            words = loadServerWordsFromSharedPreferences(context);
//          words = Arrays.asList("Word" + curPage + "1", "Word" + curPage + "2", "Word" + curPage + "3", "Word" + curPage + "4",
//                   "Word" + curPage + "5", "Word" + curPage + "6", "Word" + curPage + "7", "Word" + curPage + "8");
            currentPageLiveData.postValue(curPage);
            wordsLiveData.postValue(words);
            curPage++;  // 确保在获取数据后递增页面
        }
    }

    /**
     * @param context:
     * @return void
     * @author Lee
     * @description 获取单词总数
     * @date 2024/5/8 9:17
     */
    public void requestTestWordNum(Context context) {
        RetrofitManager.getInstance(context,WORD_SERVICE)
                .getApi(ApiService.class)
                .getWordNum()
                .enqueue(new Callback<BaseResponse<Integer>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<Integer>> call, Response<BaseResponse<Integer>> response) {
                        if(response.isSuccessful() && response.body() != null) {
                            Log.e("AAAAAAAAAA", response.body().getData().toString());

                            Integer num = response.body().getData();
                            totalPages = (int) Math.ceil(num / 8.0);  // 计算总页数
                        } else {
                            // HTTP错误处理
                            Log.e("HTTP Error", "Response Code: " + response.code() + " Message: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<Integer>> call, Throwable t) {
                        Log.e("HomeFragment-Error", "Network-Error");
                        t.printStackTrace();
                    }
                });
    }

    /**
     * @param context:
     * @param currentPage:
     * @return void
     * @author Lee
     * @description 获取测试单词
     * @date 2024/5/8 9:17
     */
    public void requestTestWords(Context context, int currentPage) {
        RetrofitManager.getInstance(context, WORD_SERVICE)
                .getApi(ApiService.class)
                .getWords(currentPage)
                .enqueue(new Callback<BaseResponse<List<WordDetail>>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<List<WordDetail>>> call, Response<BaseResponse<List<WordDetail>>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<WordDetail> data = response.body().getData();
                            List<String> words = new ArrayList<>();
                            for (WordDetail wordDetail : data) {
                                words.add(wordDetail.getWord());
                            }

                            Map<String, Integer> wordMap = new HashMap<>();
                            for(WordDetail wordDetail : data) {
                                wordMap.put(wordDetail.getWord(), wordDetail.getId());
                            }
                            saveWordsAndIds(wordMap, context);


                            // 保存数据到 SharedPreferences 并在保存完成后加载数据
                            saveServerWordsToSharedPreferences(words, context, () -> {
                                List<String> loadedWords = loadServerWordsFromSharedPreferences(context);
                                wordsLiveData.postValue(loadedWords);
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<List<WordDetail>>> call, Throwable t) {
                        Log.e("ViewModel", "Network-Error", t);
                    }
                });
    }

    /**
     * @param context:
     * @param unknowWordId:
     * @param knowWordId:
     * @return void
     * @author zhang
     * @description 将测试结果发给后端
     * @date 2024/5/8 14:13
     */
    public void sendTestResultToServer(Context context, List<Integer> unknowWordId, List<Integer> knowWordId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", TokenManager.getUserId(context));
        params.put("unKnowWordId", GsonUtils.getGsonInstance().toJson(unknowWordId));
        params.put("knowWordId", GsonUtils.getGsonInstance().toJson(knowWordId));
        RetrofitManager.getInstance(context, WORD_SERVICE)
                .getApi(ApiService.class)
                .sendTestResultToServer(params)
                .enqueue(new Callback<BaseResponse<Void>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<Void>> call, Response<BaseResponse<Void>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Log.e("AAAAAAAAAAAAAA", response.body().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<Void>> call, Throwable t) {
                        t.printStackTrace();
                        Log.e("UserTestViewModel-sendTestResultToServer", "Network-Error");
                    }
                });
    }
//    public void requestTestWords(Context context,int currentPage) {
//        RetrofitManager.getInstance(context,WORD_SERVICE)
//                .getApi(ApiService.class)
//                .getWords(currentPage)
//                .enqueue(new Callback<BaseResponse<List<WordDetail>>>() {
//                    @Override
//                    public void onResponse(Call<BaseResponse<List<WordDetail>>> call, Response<BaseResponse<List<WordDetail>>> response) {
//                        if (response.isSuccessful() && response.body() != null) {
//                            List<WordDetail> data = response.body().getData();
//                            Log.e("NNNNNNNNNNNNNN", data.toString());
//
//                            List<String> words = new ArrayList<>();
//                            for (WordDetail wordDetail : data) {
//                                words.add(wordDetail.getWord());
//                                Log.e("AAAAAAAAAA", wordDetail.getWord());
//                            }
//
//                            TokenManager.saveServerWordsToSharedPreferences(words, context.getApplicationContext());
//                            List<String> word = loadServerWordsFromSharedPreferences(context.getApplicationContext());
//                            Log.e("AAAAAAAAAA", word.toString());
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<BaseResponse<List<WordDetail>>> call, Throwable t) {
//                        Log.e("HomeViewModel-requestTestWords:", "Network-Error");
//                        t.printStackTrace();
//                    }
//                });
//    }

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
