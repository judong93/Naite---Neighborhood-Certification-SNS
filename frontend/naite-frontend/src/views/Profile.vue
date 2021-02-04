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
            <p @click="postingList" :class="{ underline: activityCheckNum===1}" class="activity"> {{ posting }} </p><p>|</p>
            <p @click="groupBuyingList" :class="{ underline: activityCheckNum===2}" class="activity"> {{ groupBuying }} </p><p>|</p>
            <p @click="commentList" :class="{ underline: activityCheckNum===3}" class="activity"> {{ comment }} </p>
          </div>
        </div>  
      </div>
      <hr>
      <div class="profile-cards">
        <div v-for="(card,idx) in Cards" :key="idx" class="profile-card">
          <div>
            <img :src="card.img" alt="" class="posting-img">
          </div>
          <div class="card-title">
            {{ card.title }}
          </div>
          <div class="card-content">
            {{ card.content }}
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

export default {
  name: 'Profile',
  data: function () {
    return {
      imgData: "https://picsum.photos/200/300",
      userName: "user1",
      posting: "게시글 6",
      groupBuying: "장터거래 6",
      comment: "댓글 6",
      Cards: [],
      postingCards: [
        {title: "**공원 뒤에서 공사한대요", img: "https://picsum.photos/200/300", content: "아이고.. 공사가 겹쳤네요. 더 시끄러워..."},
        {title: "공사한대요", img: "https://picsum.photos/200/300", content: "시끄러울 것 같아요"},
        {title: "공사한대요", img: "https://picsum.photos/200/300", content: "시끄러울 것 같아요"},
        {title: "공사한대요", img: "https://picsum.photos/200/300", content: "시끄러울 것 같아요"},
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
    }
  },
  components: {
    Navbar,
    Message
  },
  created: function () {
    this.Cards = this.postingCards
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
  padding-left: 105px;
}
#settings-icon {
  margin-left: 20px;
  cursor: pointer;
}
.profile-info {
  position: relative;
  width: 400px;
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
  text-align: left;
  margin-top: 14px;
  margin-left: 20px;
  font-weight: bold;
  font-size: 110%;
  height: 52px;
}
.card-content {
  text-align: left;
  margin-top: 8px;
  padding-left: 20px;  
  padding-right: 20px;  
  height: 48px;
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