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
          <p> {{ posting }} | </p>
          <p> &nbsp;{{ groupBuying }} | </p>
          <p> {{ comment }} </p>
        </div>
      </div>  
    </div>
    <hr>
    <div class="cards">
      <div v-for="(card,idx) in cards" :key="idx" class="card">
        <div>
          {{ card.title }}
        </div>
        <div>
          <img :src="postingImg" alt="" class="posting-img">
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
      cards: [
        {title: "공사한대요", content: "시끄러울 것 같아요"},
        {title: "글쎄요", content: "그러게요"},
        {title: "글쎄요", content: "그러게요"},
        {title: "글쎄요", content: "그러게요"},
      ],
      postingImg: "https://picsum.photos/200/300",
    }
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
  display: flex;
  align-items: center;
  flex-direction: column;
  height: 600px;
  margin-top: 120px;
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
  /* position: absolute; */
  font-size: 35px;
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
  top: 55px; 
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
  height: 200px;
}
.posting-img {
  height: 100px;
  width: 130px;
  border-radius: 10px;
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