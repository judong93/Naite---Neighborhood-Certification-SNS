<template>
  <div id="profile">
    <Navbar />
    <Message />
    <SettingBox :formIsOpen='formIsOpen' :formTitle='formTitle' :userJoinList='userJoinList' :selectedMarketNo='selectedMarketNo' :isSeller='isSeller' />
    <div class="profile">
      <div class="profile-box">
        <div @mouseover="showReliability" @mouseout="showImg" class="profile-img-container">
          <img v-if="onProfileImg===false" :src="imgData" alt="No Image" class="profile-img">
          <div v-if="onProfileImg===true" class="reliability-letter">신뢰도</div>
          <div v-if="onProfileImg===true" class="reliability">{{ userScore }}</div>
        </div>
        <div class="profile-info">
          <div class="username-settings">
            <div class="username">
            {{ userNick }}
            <i v-if="userNo===loginedUserNo" id="settings-icon" class="fas fa-user-cog" @click="selectSettings"></i>
            </div>
          </div>
          <div class="activities">
            <p @click="postingList" :class="{ underline: activityCheckNum===1}" class="activity"> 게시글{{ postingCount }} </p><p>|</p>
            <p @click="groupBuyingList" :class="{ underline: activityCheckNum===2}" class="activity"> 장터거래{{ marketCount }} </p><p>|</p>
            <p @click="commentList" :class="{ underline: activityCheckNum===3}" class="activity"> 댓글단 글{{ commentCount }} </p>
          </div>
        </div>  
      </div>
      <div class="mobile-usernick">{{ userNick }}</div>
      <div v-if="userNo===loginedUserNo" @click="selectSettings" class="mobile-profile-settings">프로필 편집</div>
      <hr>
      <div class="profile-cards-container">
        <div v-for="(userPosting,idx) in userPostings" :key=idx class="profile-cards" :class="{ carouselactive: idx===carouselNo}">
          <i @click="previousCarouselSet" v-if="carouselLength >=0" class="fas fa-chevron-left fa-2x carousel-move-btn"></i>
          <div class="profile-card-container">
            <div v-for="(card,idx) in userPosting" :key="idx" class="profile-card">
              <button @click="evalBuyer(card.marketNo, card.isSeller)" v-if="card.isSeller===0 && card.evalIsCompleted===0" class="eval-btn">평가를 부탁드려요!</button>
              <img @click="toBoardDetail(card.boardNo, card.marketNo, card.bigCategoryNo)" :src="imgData" alt="이미지가 없습니다!" class="posting-img">
              <div @click="toBoardDetail(card.boardNo, card.marketNo, card.bigCategoryNo)" class="card-title">
                {{ card.boardTitle }}
              </div>
              <div @click="toBoardDetail(card.boardNo, card.marketNo, card.bigCategoryNo)" class="card-content">
                {{ card.boardContent }}
              </div>
              <div class="card-category">{{ bigCategory[card.bigCategoryNo] }}게시글</div>
              <button v-if="activityCheckNum===1 && userNo===loginedUserNo" @click="deletePosting(card.boardNo)" class="profile-card-button cdb">삭제하기</button>
              <div v-if="card.marketIsCompleted===0" @click="changeMarketStatus(card.marketIsCompleted, card.marketNo)" 
              class="profile-card-button market-not-completed"> <span>모집중</span> </div>
              <div v-if="card.marketIsCompleted===1" 
              class="profile-card-button market-is-completed"> <span>거래완료</span> </div>
            </div>
          </div>
          <i @click="nextCarouselSet" class="fas fa-chevron-right fa-2x carousel-move-btn"></i>
        </div>  
      </div>
    </div>
  </div>

</template>

<script>
import Navbar from '@/components/Basic/Navbar'
import Message from '@/components/Basic/Message'
import axios from 'axios'
import jwt_decode from 'jwt-decode'
import SettingBox from '@/components/Profile/SettingBox'

const SERVER_URL = 'https://i4a402.p.ssafy.io/api'

export default {
  name: 'Profile',
  data: function () {
    return {
      imgData: "http://picsum.photos/200/300",
      postingCount: 0,
      marketCount: 0,
      commentCount: 0,
      userPostings: [],
      userBoardPostings: [],
      userMarketPostings: [],
      userCommentPostings: [],
      postingImg: "https://picsum.photos/200/300",
      activityCheckNum: 0,
      userNick: '',
      userScore: 0,
      userNo: 0,
      loginedUserNo: 0,
      onProfileImg: false,
      carouselNo: 0,
      carouselLength: 0,
      bigCategory: ['','번화가 ', '소리소문 ', '수군수군 ', '동사무소 ', '장터 ', '공지사항 '],
      formIsOpen: false,
      formTitle: '',
      userJoinList: [],
      selectedMarketNo: 0,
      isSeller: 5
    }
  },
  components: {
    Navbar,
    Message,
    SettingBox
  },
  methods: {
    postingList: function () {
      this.activityCheckNum = 1
      this.Cards = this.postingCards
    },
    groupBuyingList: function () {
      this.activityCheckNum = 2
      this.Cards = this.groupBuyingCards
    },
    commentList: function () {
      this.activityCheckNum = 3
      this.Cards = this.commentCards
    },
    selectSettings: function () {
      this.$router.push({ name: 'ProfileSettings' })
    },
    setToken:function(){
      const token=localStorage.getItem('jwt')
      const config = {
          headers: {
          'auth-token':`${token}`
          }
      }
      console.log(config)
      return config
    },
    deletePosting: async function (boardNo) { 
      const config = this.setToken()
      const result = confirm("정말 삭제하시겠습니까?")
      if (!result) {
        console.log(boardNo)
      } else {
        axios.put(`${SERVER_URL}/board/delete/${boardNo}`, {}, config)
          .then(() => {
            this.getBoardList()
          })
          .catch((err) => {
            console.log(err)
            alert('로그아웃 되었습니다. 다시 로그인 진행해주세요!')
            localStorage.removeItem('jwt')
            this.$router.push({name:'Sign'})
          })
        console.log(this.userBoardPostings)
      }
    },
    toBoardDetail: function (boardNo,marketNo,bigCategoryNo) {
      if (bigCategoryNo===5) {
        this.toMarketDetail(marketNo)
      } else {
        this.$router.push({ name: 'BoardDetail', params: {boardNo: boardNo} })
      }
    },
    toMarketDetail: function (marketNo) {
      this.$router.push({name:'MarketBoardDetail',params:{marketNo:marketNo}})
    },
    showReliability: function () {
      setTimeout(() => {
        this.onProfileImg = true
      }, 400);
    },
    showImg: function () {
      setTimeout(() => {
        this.onProfileImg = false
      }, 400);
    },
    nextCarouselSet: function () {
      console.log(this.carouselLength)
      if (this.carouselNo < this.carouselLength) {
        this.carouselNo += 1
      } else {
        this.carouselNo = 0
      }
    },
    previousCarouselSet: function () {
      if (this.carouselNo===0) {
        this.carouselNo = this.carouselLength
      } else {
        this.carouselNo -= 1
      }
    },
    // 각각 게시글 리스트를 하나의 함수에서 처리하면 좋았을텐데, API 주소가 규칙성이 없이 다르고, 데이터 양식도 달라서 불가피하게 다 나눔..ㅠ
    getBoardList: function () {
      axios.get(`${SERVER_URL}/board/list/user/${this.userNo}`)
        .then((res) => {
          this.postingCount = res.data.length
          const length = parseInt((res.data.length -1) / 5)
          this.carouselLength = length
          this.userBoardPostings = []
          for (let i = 0; i <= length; i++) {
            this.userBoardPostings.push(res.data.splice(0,5))
            if (i===length) {
              this.userPostings = this.userBoardPostings
            }
          }
        })
        .catch((err) => {
          console.log(err)
        })
    },
    getMarketList: function () {
      axios.get(`${SERVER_URL}/market/list/user/${this.userNo}`)
        .then((res) => {  
          this.marketCount = res.data.length
          const length = parseInt((res.data.length -1) / 5)
          this.userMarketPostings = []
          for (let i = 0; i <= length; i++) {
            this.userMarketPostings.push(res.data.splice(0,5))
            }
            if (this.activityCheckNum===2) {
              this.userPostings = this.userMarketPostings
            }
        })
        .catch((err) => {
          console.log(err)
        })
    },
    getCommentList: function () {
      axios.get(`${SERVER_URL}/comment/user/${this.userNo}`)
        .then((res) => {
          console.log(res)
          this.commentCount = res.data.data.length
          const length = parseInt((res.data.data.length -1) / 5)
          for (let i = 0; i <= length; i++) {
            this.userCommentPostings.push(res.data.data.splice(0,5))
          }
          console.log(this.userCommentPostings)
        })
        .catch((err) => {
          console.log(err)
        })
    },
    getUserInfo: function () {
      const config = this.setToken()
      axios.get(`${SERVER_URL}/user/profile/${this.userNo}`, config)
        .then((res) => {
          this.userNick = res.data.data.userNick
          this.userScore = res.data.data.userScore
        })
        .catch((err) => {
          console.log(err)
        })
    },
    evalBuyer: function (marketNo, isSeller) {
      this.formIsOpen = !this.formIsOpen
      this.formTitle = '평가를 남겨주세요!'
      this.selectedMarketNo = marketNo
      this.isSeller = isSeller
    },
    changeMarketStatus: function (status,marketNo) {
      const config = this.setToken()
      if (this.userNo === this.loginedUserNo) {
        if (status===0) {
          axios.get(`${SERVER_URL}/market/join/${marketNo}`, config)
            .then((res) => {
              console.log(res.data)
              this.selectedMarketNo = marketNo
              this.formIsOpen = !this.formIsOpen
              this.formTitle = '거래에 참여한 유저를 선택해주세요'
              this.userJoinList = res.data.data
              
            })
          // axios.put(`${SERVER_URL}/market/complete/${marketNo}`, {}, config)
          //   .then(() => {
          //     this.getMarketList()
          //     this.userPostings = this.userMarketPostings
          //   })
          
        } else {
          const result = confirm('다시 모집하시겠습니까?')
          if (result) {
            axios.put(`${SERVER_URL}/market/restore/${marketNo}`, {}, config)
              .then(() => {
                this.getMarketList()
                this.userPostings = this.userMarketPostings
              })
          }
        }
      }

    }
  },
  watch: {
    activityCheckNum: function () {
      if (this.activityCheckNum===1) {
        this.userPostings = this.userBoardPostings
        this.carouselLength = this.userBoardPostings.length -1
      } else if (this.activityCheckNum===2) {
        this.userPostings = this.userMarketPostings
        this.carouselLength = this.userMarketPostings.length -1
      } else {
        this.userPostings = this.userCommentPostings
        this,this.carouselLength = this.userCommentPostings.length -1
      }
      this.carouselNo = 0
    }
  },
  created: function () {
    this.Cards = this.postingCards
    // const config = this.setToken()
    const decode = jwt_decode(localStorage.getItem('jwt'))
    this.loginedUserNo = decode.user.userNo
    // 새로고침하면 userNo가 스트링으로 받아와짐.. 도대체 왜지?!
    this.userNo = Number(this.$route.params.userNo)
    this.getUserInfo()
    this.getBoardList()
    this.getMarketList()
    this.getCommentList()
    this.activityCheckNum = 1
  },
}
</script>
  
<style scoped>
#profile {
  position:relative;
  width: 1918px;
  height: 935px;
  overflow:hidden;
  background-color: rgb(250, 246, 240);
  font-family: font1;
}
hr {
  position: relative;
  width: 55%;
}
.profile {
  position: relative;
  display: flex;
  align-items: center;
  flex-direction: column;
  height: 70%;
  top: 20%;
}
.profile-box {
  /* position: relative; */
  /* background-color: lightgray; */
  display: flex;
  justify-content: space-evenly;
  /* top: 130px; */
  height: 180px;
  width: 60%;
}
.profile-img-container {
  position: relative;
  /* background-color: red; */
  width: 150px;
  height: 150px;
  overflow: hidden;
  border-radius: 50%;
  background-color: rgb(190, 208, 245, 0.5);
}
.profile-img {
  position: absolute;
  /* background-color: red; */
  left: 0;
  width: 100%;
  height: 100%;
  overflow: hidden;
  /* border-radius: 50%; */
}
.profile-img-container:hover {
  /* background-color: black; */
  transform: rotateY(180deg);
  transition-duration: 0.9s;
}
.profile-img-container:not(:hover) {
  transition-duration: 0.9s;
}
.profile-img-container div {
  position: absolute;
  left: 20%;
  right: 20%;
  transform: rotateY(180deg);
}
.reliability-letter {
  top: 25%;
  font-size: 175%;
}
.reliability {
  top: 60%;
  font-size: 130%;
}
.username {
  position: relative;
  font-size: 35px;
  top: 10px;
}
.username-settings {
  display: flex;
  justify-content: center;
  height: 80px;
  /* padding-left: 60px; */
}
#settings-icon {
  margin-left: 20px;
  cursor: pointer;
}
.profile-info {
  position: relative;
  width: 450px;
}
.activities {
  display: flex;
  justify-content: space-between;
  position: relative;
  top: 20px; 
  font-size: 30px;
}
.mobile-usernick {
  height: 0;
  display: none;
  text-align: left;
}
.mobile-profile-settings {
  height: 0;
  display: none;
  border: 2px lightgray solid;
}
.profile-cards-container {
  /* margin-left: 80px; */
  /* margin-top: 20px; */
  width: 60%;
  text-align: center;
}
.profile-cards {
  position: absolute;
  display: flex;
  justify-content: space-between;
  width: 60%;
  z-index: 0;
  opacity: 0;
  transition: all .5s ease-in-out;
  transform: scale(0.95);
}
.carouselactive {
  opacity: 1;
  z-index: 1;
  transform: none;
}
.profile-card-container {
  /* text-align: left; */
  width: 92%;
  display: flex;
  justify-content: center;
}
.profile-card {
  position: relative;
  display: flex;
  flex-direction: column;
  overflow: hidden;
  border: 1px solid palegoldenrod;
  width: 185px;
  height: 290px;
  margin: 0 12px;
  cursor: pointer;
}
.profile-card:hover {
  opacity: 0.5;
}
.posting-img {
  height: 38%;
}
.card-title {
  line-height: 1.5em;
  overflow: hidden;
  text-align: left;
  margin-top: 14px;
  margin-left: 10px;
  font-weight: bold;
  font-size: 120%;
  height: 52px;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.card-content {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  word-wrap:break-word; 
  line-height: 1.5em;
  overflow: hidden;
  text-align: left;
  padding-left: 10px;  
  padding-right: 20px;  
  height: 52px;
  white-space: normal;
  text-overflow: ellipsis;
}
.card-category {
  font-size: 12px;
  margin-top: 13px;
  margin-right: 20px;
  text-align: right;
}
.underline {
  text-decoration: underline;
}
.activity {
  cursor: pointer;
  /* border-radius: 40%; */
}
.activity:hover {
  background-color: rgb(223, 217, 217);
  opacity: 0.7;
}
.profile-card-button{
  /* margin-left: 100%; */
  /* margin-top: 17px; */
  width: 100%; 
  text-align: center;
  /* background:#DB4455; */
  display: flex;
  justify-content: center;
  align-items: center;
  color:#fff;
  border:none;
  position:relative;
  height: 14%;
  font-size:1.3em;
  padding:0;
  cursor:pointer;
  transition:800ms ease all;
  outline:none;
}
.profile-card-button:hover{
  background:#fff;
  /* color:#DB4455; */
}
.profile-card-button:before,.profile-card-button:after{
  content:'';
  position:absolute;
  top:0;
  right:0;
  height:2px;
  width:0;
  /* background: #DB4455; */
  transition:400ms ease all;
}
.profile-card-button:after{
  right:inherit;
  top:inherit;
  left:0;
  bottom:0;
}
.profile-card-button:hover:before,.profile-card-button:hover:after{
  width:100%;
  transition:800ms ease all;
}
.cdb {
  background:#DB4455;
}
.cdb:hover {
  color:#DB4455;
}
.cdb:before, .cdb:after{
  background:#DB4455;
}
.market-not-completed{
  background:#1AABBA;
}
.market-not-completed:hover {
  color: #1AABBA;
}
.market-not-completed:before, .market-not-completed:after {
  background:#1AABBA;
}
.market-is-completed{
  background:#FF7F50;
}
.market-is-completed:hover {
  color: #FF7F50;
}
.market-is-completed:before, .market-is-completed:after {
  background:#FF7F50;
}
#settings-icon:hover {
  font-size: 130%;
  transition-duration: 0.3s;
}
#settings-icon:not(:hover) {
  transition-duration: 0.3s;
}
.carousel-move-btn {
  position: relative;
  margin-top: 110px;
  cursor: pointer;
}
.carousel-move-btn:hover {
  font-size: 240%;
  transition-duration: 0.3s;
}
.eval-btn {
  position: absolute;
  width: 50%;
  height: 16%;
  z-index: 3;
  top: 10%;
  left: 50%;
  color:white;
  background-color: rgb(102, 102, 245);
  border-radius: 10px;
  border: none;
  transform:translateX(-50%);
} 
.eval-btn:hover {
  width: 60%;
  height: 20%;
  transition-duration: 0.3s;
}

@media screen and (max-width: 501px) {
  #profile {
    width: 100vw;
    height: 100vh;
  }
  .profile {
    top: 13%;
    height: 75%;
  }
  .profile-box {
    width: 80%;
    justify-content: inherit;
    height: 15%;
  }
  .profile-img-container {
    margin: 0 20px;
    width: 80px;
    height: 80px;
    font-size: 10px;
  }
  .profile-info {
    width: 230px;
    margin-left: 10px;
    height: 100px;
  }
  .activities {
    font-size: 14px;
    top: 26px;
  }
  .username-settings {
    display: none;
  }
  .mobile-usernick {
    display: block;
    height: 20px;
    width: 70%;
  }
  .mobile-profile-settings {
    display: block;
    margin-top: 15px;
    height: 30px;
    width: 70%;
    border-radius: 5px;
    cursor: pointer;
  }
  hr {
    width: 72%;
  }
  .profile-cards-container {
    position: relative;
    /* margin-top: 5px; */
    width: 80%;
    height: 70%;
    overflow: auto;
  }
  .profile-cards-container::-webkit-scrollbar {
    display: none;
  }
  .carousel-move-btn {
    display: none;
  }
  .profile-cards {
    display: block;
    position: relative;
    width: 100%;
    opacity: 1;
    z-index: 1;
    transform: none;
  }
  .profile-card-container {
    flex-wrap: wrap;
    width: 100%;
  }
  .profile-card {
    height: 190px;
    width: 112px;
    margin: 0;
    font-size: 7.5px;
    margin: 1px;
  }
  .profile-card:hover {
    height: 190px;
    width: 112px;
    opacity: 0.5;
  }
  .card-title {
    height: 14px;
    margin-bottom: 12px;
  }
  .card-content {
    height: 22px;
  }
  .card-category {
    font-size: 7px;
  }
  .profile-card-button {
    font-size: 13px;
  }
}
</style>