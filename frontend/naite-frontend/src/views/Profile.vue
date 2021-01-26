<template>
  <div class="profile">
    <div class="profile-box">
      <div class="img-container">
        <img :src="imgData" alt="No Image" class="profile-img">
      </div>
      <div class="profile-info">
        <div class="username">
        {{ userName }}
        </div>
        <div class="activities">
          <p @click="postingList" :class="{ underline: activityCheckNum===1}" class="activity"> {{ posting }} </p><p>|</p>
          <p @click="groupBuyingList" :class="{ underline: activityCheckNum===2}" class="activity"> {{ groupBuying }} </p><p>|</p>
          <p @click="commentList" :class="{ underline: activityCheckNum===3}" class="activity"> {{ comment }} </p>
        </div>
      </div>  
    </div>
    <hr>
    <div class="cards">
      <div v-for="(card,idx) in Cards" :key="idx" class="card">
        <div>
          {{ card.title }}
        </div>
        <div>
          <img :src="card.img" alt="" class="posting-img">
        </div>
        <div>
          {{ card.content }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Profile',
  data: function () {
    return {
      imgData: "https://picsum.photos/200/300",
      userName: "Naite",
      posting: "게시글 3",
      groupBuying: "장터거래 4",
      comment: "댓글 4",
      Cards: [],
      postingCards: [
        {title: "공사한대요", img: "https://picsum.photos/200/300", content: "시끄러울 것 같아요"},
        {title: "공사한대요", img: "https://picsum.photos/200/300", content: "시끄러울 것 같아요"},
        {title: "공사한대요", img: "https://picsum.photos/200/300", content: "시끄러울 것 같아요"},
      ],
      groupBuyingCards: [
        {title: "장터거래", img: "https://picsum.photos/200/300", content: "장터거래"},
        {title: "장터거래", img: "https://picsum.photos/200/300", content: "장터거래"},
        {title: "장터거래", img: "https://picsum.photos/200/300", content: "장터거래"},
      ],
      commentCards: [
        {title: "댓글단 글", img: "https://picsum.photos/200/300", content: "댓글단 글"},
        {title: "댓글단 글", img: "https://picsum.photos/200/300", content: "댓글단 글"},
        {title: "댓글단 글", img: "https://picsum.photos/200/300", content: "댓글단 글"},
      ],
      postingImg: "https://picsum.photos/200/300",
      activityCheckNum: 0,
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
  },
  components: {
    
  }
}
</script>
  
<style>
hr {
  position: relative;
  width: 700px;
}
.profile {
  position: relative;
  display: flex;
  align-items: center;
  flex-direction: column;
  height: 600px;
  top: 110px;
}
.profile-box {
  /* position: relative; */
  /* background-color: lightgray; */
  display: flex;
  /* justify-content: space-evenly; */
  /* top: 130px; */
  height: 180px;
  width: 900px;
}
.img-container {
  position: relative;
  background-color: red;
  width: 150px;
  height: 150px;
  left: 100px;
  overflow: hidden;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
}
.img-container:hover {
  background-color: black;
  transform: rotateY(180deg);
}
.username {
  position: relative;
  font-size: 35px;
  top: 10px;
}
.profile-info {
  position: relative;
  left: 200px;
  width: 400px;
}
.activities {
  display: flex;
  justify-content: space-between;
  position: relative;
  top: 38px; 
  font-size: 30px;
}
.cards {
  margin-top: 20px;
  display: flex;
  justify-content: space-around;
  width: 900px;
}
.card {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  border: 2px solid lightgray;
  border-radius: 10px;
  width: 170px;
  height: 250px;
}
.posting-img {
  height: 100px;
  width: 130px;
  border-radius: 10px;
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
  .cards {
    width: 400px;
  }
  .card {
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