<template>
  <div id="profile-img-changing-form">
    <form action="#">
      <input type="file" class="profile-img-changing-input" @change="changeProfileImg">

      <div v-if='userPic'>
        <img :src="image" alt="" width='150px' height='150px' style='border-radius:100%'>
      </div>


      <button class="setting-btn" @click='changeProfile'>프로필 이미지 변경</button>
    </form>
  </div>
</template>

<script>
import axios from 'axios'

const SERVER_URL = 'https://i4a402.p.ssafy.io/api'

export default {
  name: 'ProfileImgChangingForm',
  data: function () {
    return {
      userPic:'',
      image:'',
    }
  },
  methods: {
    changeProfileImg:function(e){
      var files = e.target.files || e.dataTransfer.files;
      if (!files.length) {
          this.userPic = ''
          return;
      }
      this.userPic = files[0] 
      if (!this.userPic){
        return ;
      }

      var reader = new FileReader()
      let that = this
      reader.onload=(e)=>{
          that.image = e.target.result
      }
      reader.readAsDataURL(this.userPic)
      

    },
    changeProfile:function(){
      let formData = new FormData
      formData.append('files',this.userPic)
      axios.put(`${SERVER_URL}/user/profile/pic`,formData,this.setToken())
        .then((res) => {
            alert('프로필이미지 변경완료')
            this.userPic = ''
            this.$emit('changingImgCompleted',res.data.data)
        })
        .catch(err=>{
          console.log(err)
        })     
    },
    checkSpace:function () {
      event.returnValue=false;
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
  }
}
</script>

<style>
  #profile-img-changing-form {

  }
  .profile-img-changing-input {
    width: 70%;
  }
  #profile-img-changing-form button {
    
  }
</style>