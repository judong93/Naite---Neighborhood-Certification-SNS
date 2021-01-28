<template>
  <div>
    <Navbar />
    <BoardImg :messagetitle='messagetitle' :message='message' :imgsrc='imgsrc' :onDetail='onDetail'/>
    <Message />
    <div style="display: flex; justify-content: center;">
      <div class="postingform-box">
        <span class="category">
          <div @click="selectFreeBoard" :class="{ underline: selectedCategoryNum===1 }">
            번화가
          </div>
          <div @click="selectQuestionBoard" :class="{ underline: selectedCategoryNum===2 }">
            동사무소
          </div>
          <div class="review">
            <p @click="selectReviewBoard" :class="{ underline: selectedCategoryNum===3 }">수군수군</p>
            <div @click="selectReviewRestaurant" :class="{ underline: selectedReviewCategoryNum===1}">식당</div>
            <div @click="selectReviewSports" :class="{ underline: selectedReviewCategoryNum===2}">체육시설</div>
            <div @click="selectReviewMedical" :class="{ underline: selectedReviewCategoryNum===3}">의료</div>
            <div @click="selectReviewBeauty" :class="{ underline: selectedReviewCategoryNum===4}">미용</div>
            <div @click="selectReviewEtc" :class="{ underline: selectedReviewCategoryNum===5}">기타</div>
          </div>
          <div @click="selectIssueBoard" :class="{ underline: selectedCategoryNum===4 }">
            소리소문
          </div>
          <div @click="selectGroupBuyingBoard" :class="{ underline: selectedCategoryNum===5 }">
            장터
          </div>
        </span>
        <span class="postingform">
          <div class="title">
            <span class="title-letter">글 제목:</span>
            <input class="title-input" type="text">
            <button class="post-button">등록</button>
          </div>
          <div class="attach-img">
            <span class="attach-img-letter">사진 첨부</span>
            <button class="attach-img-button">내PC</button>
          </div>
          <div class="text-style">
            <span>바탕체</span>
            <span>10pt</span>
            <span>굵게</span>
            <span>밑줄</span>
            <span>기울임</span>
            <span>글자색깔</span>
            <span>왼쪽정렬</span>
            <span>가운데정렬</span>
            <span>오른쪽정렬</span>
          </div>
          <textarea name="contentInput" id="" cols="30" rows="10" class="content-input"></textarea>
        </span>
      </div>
    </div>
  </div>
</template>

<script>
import Navbar from '@/components/Navbar'
import Message from '@/components/Message'
import BoardImg from '@/components/BoardImg'

export default {
  name: 'PostingForm',
  data: function () {
    return {
      messagetitle:'나의 이웃테두리: 나이테',
      message:'당신이 모르는 동네이야기',
      imgsrc: 'boardimg-main.png',
      onDetail: false,
      selectedCategoryNum: 0,
      selectedReviewCategoryNum: 0,
    }
  },
  methods: {
    selectFreeBoard: function () {
      this.selectedCategoryNum=1
      this.selectedReviewCategoryNum=0
    },
    selectQuestionBoard: function () {
      this.selectedCategoryNum=2
      this.selectedReviewCategoryNum=0
    },
    selectReviewBoard: function () {
      this.selectedCategoryNum=3
      this.selectedReviewCategoryNum=1
    },
    selectIssueBoard: function () {
      this.selectedCategoryNum=4
      this.selectedReviewCategoryNum=0
    },
    selectGroupBuyingBoard: function () {
      this.selectedCategoryNum=5
      this.selectedReviewCategoryNum=0
    },
    selectReviewRestaurant: function () {
      this.selectedCategoryNum=3
      this.selectedReviewCategoryNum=1
    },
    selectReviewSports: function () {
      this.selectedCategoryNum=3
      this.selectedReviewCategoryNum=2
    },
    selectReviewMedical: function () {
      this.selectedCategoryNum=3
      this.selectedReviewCategoryNum=3
    },
    selectReviewBeauty: function () {
      this.selectedCategoryNum=3
      this.selectedReviewCategoryNum=4
    },
    selectReviewEtc: function () {
      this.selectedCategoryNum=3
      this.selectedReviewCategoryNum=5
    },
  },
  components: {
    Navbar,
    Message,
    BoardImg,
  },
}
</script>

<style>
.postingform-box {
  position: relative;
  overflow: auto;
  /* background-color: lightgray; */
  top: 285px;
  height: 600px;
  width: 1530px;
}
.category {
  position: absolute;
  background-color: #ffb5b5;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  top: 0;
  left: 0;
  height: 600px;
  width: 350px;
  font-size: 25px;
  margin: 0;
}
.category > div {
  cursor: pointer;
}
.review {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
}
.review > div {
  font-size: 17px;
  margin: 4px 0;
}
.postingform {
  position: absolute;
  display: flex;
  flex-direction: column;
  background-color: lightyellow;
  left: 350px;
  height: 600px;
  width: 1180px;
}
.postingform > div {
  padding: 12px 20px 12px 40px;
  font-size: 20px;
  height: 57px;
  display: flex;
  border-bottom: rgb(160, 160, 81, 0.5) solid 2px;
}
.title > input {
  position: relative;
  border: white;
  width: 85%;
  padding-top: 0;
}
.title-letter {
  margin-right: 20px;
}
.post-button {
  margin-left: 18px;
}
.attach-img-button {
  margin-left: 18px;
}
.text-style > span {
  border: rgb(160, 160, 81, 0.5) solid 2px;
  margin-right: 8px;
}
.content-input {
  margin: 23px;
  margin-left: 40px;
  border: white;
  height: inherit;
  outline: none;
}
.underline {
  text-decoration: underline;
}
</style>