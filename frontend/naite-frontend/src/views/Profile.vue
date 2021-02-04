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
          <img :src="card.boardpic" alt="이미지가 없습니다!" class="posting-img">
          <div class="card-title">
            {{ card.boardTitle }}
          </div>
          <div class="card-content">
            {{ card.boardContent }}
          </div>
          <button class="card-delete-button">DELETE</button>
        </div>
      </div>
      <div v-if="activityCheckNum===2" class="profile-cards">
        <div v-for="(card,idx) in userMarketPostings" :key="idx" class="profile-card">
          <img :src="card.img" alt="이미지가 없습니다!" class="posting-img">
          <div class="card-title">
            {{ card.board.boardTitle }}
          </div>
          <div class="card-content">
            {{ card.board.boardContent }}
          </div>
          <button class="card-delete-button">DELETE</button>
        </div>
      </div>
      <div v-if="activityCheckNum===3" class="profile-cards">
        <div v-for="(card,idx) in userCommentPostings" :key="idx" class="profile-card">
          <img :src="card.img" alt="이미지가 없습니다!" class="posting-img">
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
      postingCards: [
        {title: "**공원 뒤에서 공사한대요", img: "https://picsum.photos/200/300", content: "아이고.. 공사가 겹쳤네요. 더 시끄러워..."},
        {title: "공사한대요", img: "https://picsum.photos/200/300", content: "시끄러울 것 같아요"},
        {title: "공사한대요", img: "https://picsum.photos/200/300", content: "시끄러울 것 같아요"},
        {title: "공사한대요", img: "", content: "시끄러울 것 같아요"},
        {title: "공사한대요", img: "https://picsum.photos/200/300", content: "시끄러울 것 같아요"},
        {title: "공사한대요", img: "https://picsum.photos/200/300", content: "시끄러울 것 같아요"},
      ],
      groupBuyingCards: [
        {title: "장터거래", img: "https://picsum.photos/200/300", content: "장터거래"},
        {title: "장터거래", img: "https://picsum.photos/200/300", content: "장터거래"},
        {title: "장터거래", img: "https://picsum.photos/200/300", content: "장터거래"},
        {title: "장터거래", img: "https://picsum.photos/200/300", content: "장터거래"},
        {title: "장터거래", img: "https://picsum.photos/200/300", content: "장터거래"},
        {title: "장터거래", img: "https://picsum.photos/200/300", content: "장터거래"},
      ],
      commentCards: [
        {title: "댓글단 글", img: "https://picsum.photos/200/300", content: "댓글단 글"},
        {title: "댓글단 글", img: "https://picsum.photos/200/300", content: "댓글단 글"},
        {title: "댓글단 글", img: "https://picsum.photos/200/300", content: "댓글단 글"},
        {title: "댓글단 글", img: "https://picsum.photos/200/300", content: "댓글단 글"},
        {title: "댓글단 글", img: "https://picsum.photos/200/300", content: "댓글단 글"},
        {title: "댓글단 글", img: "https://picsum.photos/200/300", content: "댓글단 글"},
      ],
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
        this.userPostings = res.data.slice(-7, -1)
        console.log(this.userPostings)
      })
      .catch((err) => {
        console.log(err)
      })
    axios.get('http://i4a402.p.ssafy.io:8080/market/list/user', config)
      .then((res) => {
        this.marketCount = res.data.length
        this.userMarketPostings = res.data
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
  margin-top: 20px;
  display: flex;
  justify-content: space-around;
  width: 60%;
}
.profile-card {
  display: flex;
  flex-direction: column;
  overflow: hidden;
  /* justify-content: space-between; */
  border: 2px solid lightgray;
  /* border-radius: 10%; */
  width: 170px;
  height: 280px;
}
.posting-img {
  height: 95px;
  width: 100%;
  /* border-radius: 10%; */
}
.card-title {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  word-wrap:break-word; 
  line-height: 1.5em;
  overflow: hidden;
  text-align: left;
  margin-top: 14px;
  margin-left: 10px;
  font-weight: bold;
  font-size: 110%;
  height: 52px;
  /* white-space: pre-line; */
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
  height: 48px;
  white-space: normal;
  text-overflow: ellipsis;
  /* white-space: normal; */
}
.card-delete-button {
  margin-left: 55%;
  position: relative;
  margin-top: 17px;
  width: 45%;
  /* border-radius: 10%; */
  color: rgb(199, 5, 5);
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