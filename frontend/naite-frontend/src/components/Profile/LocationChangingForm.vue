<template>
  <div id="location-changing-form">
    <form action="#">
      <label for="#">주소</label><br>
      <input type="text" 
      v-model="changeAddressParams.userBasicAddress" @click="openSearchLocation" 
      placeholder="클릭하여 주소를 검색해주세요" readonly><br>
      <label for="#">상세주소</label><br>
      <input type="text"
      v-model="changeAddressParams.userDetailAddress" @keypress.enter="changeAddress"><br>
      <button class="setting-btn" @click="changeAddress">확인</button>
    </form>
    <Location :searchLocation='searchLocation' @selectAddress = 'selectAddress' @checkAddress='checkAddress' />
  </div>

</template>

<script>
import Location from '@/components/Sign/Location'
import axios from 'axios'

const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: 'LocationChangingForm',
  data: function () {
    return {
      changeAddressParams: {
        userBasicAddress: '',
        userDetailAddress: '',
        userDong: '',
      },
      searchLocation: false,
      addressConfirm: false,
    }
  },
  components:{
    Location,                
  },
  methods: {
    setToken:function(){
      const token=localStorage.getItem('jwt')
      const config = {
          headers: {
          'auth-token':`${token}`
          }
      }      
      return config
    },
    checkSpace:function () {
    event.returnValue=false;
    },
    openSearchLocation:function(){
      if (this.searchLocation) {
          this.searchLocation = false
      } else {
          this.searchLocation= true
      }
    },
    selectAddress:function(result) {
      this.searchLocation = false
      this.changeAddressParams.userBasicAddress = result.address
      this.changeAddressParams.userDong = result.bname2
    },
    checkAddress:function(res) {
      this.addressConfirm = res
    },
    changeAddress: function () {
      const config = this.setToken()
      if (this.changeAddressParams.userDetailAddress==='') {
        alert('상세주소를 입력해주세요!')
      } else if (!this.addressConfirm) {
        alert('주소를 정확히 입력해주세요')
      } else {
        const params = this.changeAddressParams
        axios.put(`${SERVER_URL}/user/profile/village`, params , config)
          .then(() => {
            alert('동네가 변경되었습니다!')
            this.$emit('changingAddressCompleted')
          })
          .catch((err) => {
            console.log(err)
          })
      }

    }  
  }

}
</script>

<style>
#location-changing-form input::placeholder {
  color: black;
  text-align: center;
}
#location-changing-form input {
  width: 70%;
}
</style>