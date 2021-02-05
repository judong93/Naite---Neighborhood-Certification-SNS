<template>
  <div id="profile">
    <Navbar />
    <Message />
    <div class="profile">
      <div class="profile-box">
        <img :src="imgData" alt="No Image" class="profile-img">
        <div class="profile-info">
          <div class="username-settings">
            <div class="username">
            {{ userName }}
            <i id="settings-icon" class="fas fa-user-cog" @click="selectSettings"></i>
            </div>
          </div>
          <div class="activities">
            <p @click="postingList" :class="{ underline: activityCheckNum===1}" class="activity"> 게시글{{ postingCount }} </p><p>|</p>
            <p @click="groupBuyingList" :class="{ underline: activityCheckNum===2}" class="activity"> 장터거래{{ marketCount }} </p><p>|</p>
            <p @click="commentList" :class="{ underline: activityCheckNum===3}" class="activity"> 댓글단 글{{ commentCount }} </p>
          </div>
        </div>  
      </div>
      <hr>
      <div v-if="activityCheckNum===1" class="profile-cards">
        <div v-for="(card,idx) in userPostings" :key="idx" class="profile-card">
          <img @click="toBoardDetail(card.boardNo)" :src="imgData" alt="이미지가 없습니다!" class="posting-img">
          <div @click="toBoardDetail(card.boardNo)" class="card-title">
            {{ card.boardTitle }}
          </div>
          <div @click="toBoardDetail(card.boardNo)" class="card-content">
            {{ card.boardContent }}
          </div>
          <button @click="deletePosting(card.boardNo)" class="card-delete-button">삭제하기</button>
        </div>
      </div>
      <div v-if="activityCheckNum===2" class="profile-cards">
        <div v-for="(card,idx) in userMarketPostings" :key="idx" class="profile-card">
          <img :src="imgData" alt="이미지가 없습니다!" class="posting-img">
          <div class="card-title">
            {{ card.board.boardTitle }}
          </div>
          <div class="card-content">
            {{ card.board.boardContent }}
          </div>
          <div v-if="card.marketIsCompleted===0" class="market-is-completed">모집중</div>
          <div v-if="card.marketIsCompleted===1" class="font-yellow market-is-completed">거래완료</div>
        </div>
      </div>
      <div v-if="activityCheckNum===3" class="profile-cards">
        <div v-for="(card,idx) in userCommentPostings" :key="idx" class="profile-card">
          <img :src="imgData" alt="이미지가 없습니다!" class="posting-img">
          <div class="card-title">
            {{ card.board.boardTitle }}
          </div>
          <div class="card-content">
            {{ card.board.boardContent }}
          </div>
          <button class="card-delete-button">DELETE</button>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
import Navbar from '@/components/Basic/Navbar'
import Message from '@/components/Basic/Message'
import axios from 'axios'

export default {
  name: 'Profile',
  data: function () {
    return {
      imgData: "https://picsum.photos/200/300",
      userName: "user1",
      postingCount: 0,
      marketCount: 0,
      commentCount: 0,
      userPostings: [],
      userMarketPostings: [],
      userCommentPostings: [],
      postingImg: "https://picsum.photos/200/300",
      activityCheckNum: 1,
    }
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
    deletePosting: function (boardNo) { 
      const config = this.setToken()
      const result = confirm("정말 삭제하시겠습니까?")
      if (!result) {
        console.log(boardNo)
      } else {
          axios.put(`http://i4a402.p.ssafy.io:8080/board/delete/${boardNo}`, {}, config)
            .then((res) => {
              console.log(res)
              axios.get('http://i4a402.p.ssafy.io:8080/board/list/user', config)
                .then((res) => {
                  this.postingCount = res.data.length
                  if (this.postingCount > 5) {
                    this.userPostings = res.data.slice(0, 5)
                  } else {
                    this.userPostings = res.data
                  }
                })
            })
            .catch((err) => {
              console.log(err)
            })
      }

    },
    toBoardDetail: function (boardNo) {
      this.$router.push({ name: 'BoardDetail', params: {boardNo: boardNo} })
    },
  },
  components: {
    Navbar,
    Message
  },
  created: function () {
    this.Cards = this.postingCards
    const config = this.setToken()
    axios.get('http://i4a402.p.ssafy.io:8080/board/list/user', config)
      .then((res) => {
        this.postingCount = res.data.length
        if (this.postingCount > 5) {
          this.userPostings = res.data.slice(0, 5)
        } else {
          this.userPostings = res.data
        }
      })
      .catch((err) => {
        console.log(err)
      })
    axios.get('http://i4a402.p.ssafy.io:8080/market/list/user', config)
      .then((res) => {
        this.marketCount = res.data.length
        if (this.marketCount > 5) {
          this.userMarketPostings = res.data.slice(-6, -1)
        } else {
          this.userMarketPostings = res.data
        }
        console.log(this.userMarketPostings)
      })
      .catch((err) => {
        console.log(err)
      })
    axios.get('http://i4a402.p.ssafy.io:8080/comment/user', config)
      .then((res) => {
        console.log(res)
      })
      .catch((err) => {
        console.log(err)
      })
  }
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
  height: 15%;
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
.profile-img {
  position: relative;
  background-color: red;
  width: 150px;
  height: 150px;
  overflow: hidden;
  border-radius: 50%;
}
.profile-img:hover {
  background-color: black;
  transform: rotateY(180deg);
}
.username {
  position: relative;
  font-size: 35px;
  top: 10px;
}
.username-settings {
  display: flex;
  justify-content: center;
  padding-left: 60px;
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
  top: 38px; 
  font-size: 30px;
}
.profile-cards {
  /* overflow: hidden; */
  margin-left: 80px;
  margin-top: 20px;
  display: flex;
  /* justify-content: space-around; */
  width: 60%;
}
.profile-card {
  display: flex;
  flex-direction: column;
  overflow: hidden;
  /* justify-content: space-between; */
  border: 1px solid palegoldenrod;
  /* border-radius: 10%; */
  width: 190px;
  height: 280px;
  margin-right: 30px;
  /* box-shadow: inset 0 0 5px rosybrown; */
  cursor: pointer;
}
.posting-img {
  height: 38%;
  width: 100%;
  /* border-radius: 10%; */
}
.card-title {
  /* display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  word-wrap:break-word;  */
  line-height: 1.5em;
  overflow: hidden;
  text-align: left;
  margin-top: 14px;
  margin-left: 10px;
  font-weight: bold;
  font-size: 125%;
  height: 52px;
  white-space: nowrap;
  text-overflow: ellipsis;
  /* word-break: normal; */
}
.card-content {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  word-wrap:break-word; 
  line-height: 1.5em;
  overflow: hidden;
  text-align: left;
  margin-top: 8px;
  padding-left: 10px;  
  padding-right: 20px;  
  height: 52px;
  white-space: normal;
  text-overflow: ellipsis;
  /* white-space: normal; */
}
/* .card-delete-button {
  margin-left: 55%;
  position: relative;
  margin-top: 17px;
  width: 45%;
  border-radius: 10%;
  color: rgb(199, 5, 5);
} */
.font-yellow {
  color: rgb(112, 112, 28);
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
.card-delete-button{
  /* margin-left: 100%; */
  margin-top: 17px;
  width: 100%; 
  text-align: center;
  background:#DB4455;
  color:#fff;
  border:none;
  position:relative;
  height: 40px;
  font-size:1.3em;
  padding:0;
  cursor:pointer;
  transition:800ms ease all;
  outline:none;
}
.card-delete-button:hover{
  background:#fff;
  color:#DB4455;
}
.card-delete-button:before,.card-delete-button:after{
  content:'';
  position:absolute;
  top:0;
  right:0;
  height:2px;
  width:0;
  background: #DB4455;
  transition:400ms ease all;
}
.card-delete-button:after{
  right:inherit;
  top:inherit;
  left:0;
  bottom:0;
}
.card-delete-button:hover:before,.card-delete-button:hover:after{
  width:100%;
  transition:800ms ease all;
}
.market-is-completed{
  /* margin-left: 100%; */
  margin-top: 17px;
  width: 100%; 
  text-align: center;
  background:#1AABBA;
  color:#fff;
  border:none;
  position:relative;
  height: 40px;
  font-size:1.3em;
  padding: 0 5px;
  /* cursor:pointer; */
  transition:800ms ease all;
  outline:none;
}


@media screen and (max-width:767px) {
  .profile {
    width: 767px;
  }
  .profile-box {
    width: 400px;
  }
  .img-container {
    width: 75px;
    height: 75px;
    left: 50px;
  }
  .username {
    font-size: 17px;
  }
  .activities {
    font-size: 15px;
  }
  .profile-cards {
    width: 400px;
  }
  .profile-card {
    width: 85px;
    height: 100px;
    font-size: 5px;
  }
  .posting-img {
    height: 50px;
    width: 65px;  
  }
  hr {
    widows: 350px;
  }
}
</style>