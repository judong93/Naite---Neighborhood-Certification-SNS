<template>
  <div>
    <!-- <Navbar /> -->
    <BoardImg :messagetitle='messagetitle' :message='message' :imgsrc='imgsrc' :onDetail='onDetail'/>
    <Message />
    <div style="display: flex; justify-content: center;">
      <div class="postingform-box">
        <span class="category">
          <div @click="selectFreeBoard" :class="{ underline: bigCategoryNo===1 }">
            번화가
          </div>
          <div @click="selectQuestionBoard" :class="{ underline: bigCategoryNo===2 }">
            동사무소
          </div>
          <div class="review">
            <p @click="selectReviewBoard" :class="{ underline: bigCategoryNo===3 }">수군수군</p>
            <div @click="selectReviewRestaurant" :class="{ underline: smallCategoryNo===1}">식당</div>
            <div @click="selectReviewSports" :class="{ underline: smallCategoryNo===2}">의료</div>
            <div @click="selectReviewMedical" :class="{ underline: smallCategoryNo===3}">체육시설</div>
            <div @click="selectReviewBeauty" :class="{ underline: smallCategoryNo===4}">미용</div>
            <div @click="selectReviewEtc" :class="{ underline: smallCategoryNo===5}">기타</div>
          </div>
          <div @click="selectIssueBoard" :class="{ underline: bigCategoryNo===4 }">
            소리소문
          </div>
          <div class="market">
            <p @click="selectMarketBoard" :class="{ underline: bigCategoryNo===5}">장터</p>
            <div @click="selectGroupBuying" :class="{ underline: smallCategoryNo===6}">공동구매</div>
            <div @click="selectSecondhand" :class="{ underline: smallCategoryNo===7}">중고거래</div>
          </div>
        </span>
        <span class="postingform">
          <div class="title">
            <span class="title-letter">글 제목:</span>
            <input v-model="boardTitle" class="title-input" type="text">
            <button @click="post" class="post-button">등록</button>
          </div>
          <div class="attach-img">
            <span class="attach-img-letter">사진 첨부</span>
            <button class="attach-img-button">내PC</button>
            <p class="rating-letter" v-if="bigCategoryNo===3">평점을 등록해주세요!</p>
            <StarRating @selectStar="selectStar" v-if="bigCategoryNo===3" />
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
          <textarea v-model="boardContent" name="contentInput" id="" cols="30" rows="10" class="content-input"></textarea>
        </span>
      </div>
    </div>
  </div>
</template>

<script>
// import Navbar from '@/components/Navbar'
import Message from '@/components/Message'
import BoardImg from '@/components/BoardImg'
import StarRating from '@/components/StarRating'
import axios from 'axios'

export default {  
  name: 'PostingForm',
  components: {
    // Navbar,
    Message,
    BoardImg,
    StarRating,
  },
  data: function () {
    return {
      messagetitle:'나의 이웃테두리: 나이테',
      message:'당신이 모르는 동네이야기',
      imgsrc: 'boardimg-main.png',
      onDetail: false,
      bigCategoryNo: 0,
      smallCategoryNo: 0,
      boardTitle: '',
      boardContent: '',
      boardPic: '',
      openFlag: 0,
      unknownFlag: 0,
      userNo: 1,
      marketCost: 0,
      marketPerson: 0,
      marketPlace: '',
      ratedStar: 0
    }
  },
  methods: {
    selectStar: function (star) {
      this.ratedStar=star
      console.log(this.ratedStar)
    },
    selectFreeBoard: function () {
      this.bigCategoryNo=1
      this.smallCategoryNo=0
      this.unknownFlag=1
    },
    selectQuestionBoard: function () {
      this.bigCategoryNo=2
      this.smallCategoryNo=0
      this.unknownFlag=0
    },
    selectReviewBoard: function () {
      this.bigCategoryNo=3
      this.smallCategoryNo=1
      this.unknownFlag=0
    },
    selectIssueBoard: function () {
      this.bigCategoryNo=4
      this.smallCategoryNo=0
      this.unknownFlag=0
    },
    selectMarketBoard: function () {
      this.bigCategoryNo=5
      this.smallCategoryNo=6
      this.unknownFlag=0
    },
    selectGroupBuying: function () {
      this.bigCategoryNo=5
      this.smallCategoryNo=6
      this.unknownFlag=0
    },
    selectSecondhand: function () {
      this.bigCategoryNo=5
      this.smallCategoryNo=7
      this.unknownFlag=0
    },
    selectReviewRestaurant: function () {
      this.bigCategoryNo=3
      this.smallCategoryNo=1
      this.unknownFlag=0
    },
    selectReviewSports: function () {
      this.bigCategoryNo=3
      this.smallCategoryNo=2
      this.unknownFlag=0
    },
    selectReviewMedical: function () {
      this.bigCategoryNo=3
      this.smallCategoryNo=3
      this.unknownFlag=0
    },
    selectReviewBeauty: function () {
      this.bigCategoryNo=3
      this.smallCategoryNo=4
      this.unknownFlag=0
    },
    selectReviewEtc: function () {
      this.bigCategoryNo=3
      this.smallCategoryNo=5
      this.unknownFlag=0
    },

    // setToken: function () {
    //   const token = localStorage.getItem('jwt')

    //   const config = {
    //     headers: {
    //       Authorization: `JWT ${token}`
    //     }
    //   }
    //   return config
    // },
    post: function () {
      // const config = this.setToken()
      const posting = {
        bigCategoryNo: this.bigCategoryNo,
        boardContent: this.boardContent,
        boardPic: this.boardPic,
        boardTitle: this.boardTitle,
        openFlag: this.openFlag, 
        unknownFlag: this.unknownFlag,
        userNo: this.userNo 
      }
      console.log(this.smallCategoryNo)
      // 리뷰 게시판
      if (0 < this.smallCategoryNo && this.smallCategoryNo < 5) {
        const reviewPosting = {
          board: posting,
          reviewStar: this.ratedStar,
          smallCategoryNo: this.smallCategoryNo
        }
        axios.post('http://localhost:8080/review/insert', reviewPosting)
          .then((res) => {
            console.log(res)
            this.$router.push({ name: 'Main' })
          })
          .catch((err) => {
            console.log(err)
          })
      // 스몰카테고리가 6이상이면, 장터 거래
      } else if (5 < this.smallCategoryNo) {
        const marktePosting = {
          board: posting,
          marketCost: this.marketCost,
          smallCategoryNo: this.smallCategoryNo
        }
        axios.post('http://localhost:8080/market/insert', marktePosting)
          .then((res) => {
            console.log(res)
            this.$router.push({ name: 'Main' })
          })
          .catch((err) => {
            console.log(err)
          })
      // 그 외 게시판
      } else {
        console.log(posting)
        axios.post('http://localhost:8080/board/insert', posting)
          .then((res) => {
            console.log(res)
            this.$router.push({ name: 'Main' })
          })
          .catch((err) => {
            console.log(err)
          })
      }
    },
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
  background-color: #3F9F47;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  top: 0;
  left: 0;
  height: 600px;
  width: 300px;
  font-size: 25px;
  margin: 0;
  color: aliceblue;
  font-weight: bold;
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
.market > div {
  font-size: 17px;
  margin: 4px 0;
}
.postingform {
  position: absolute;
  display: flex;
  flex-direction: column;
  background-color: lightyellow;
  left: 300px;
  height: 600px;
  width: 1230px;
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
.rating-letter {
  margin-left: 100px;
  margin-right: 20px;
}
</style>