<template>
    <div id="location" v-if='searchLocation'>
    
        <div style='position:relative'>
            <VueDaumPostcode
            style='width:500px;max-height:700px; 
            backgroudColor:red;position:fixed; 
            top:50%;left:50%; 
            overflow:auto;
            transform: translate(-50%,-50%);
            ' 
            id = 'locationSearch'
            @complete='result=$event'
            :theme="{
                bgColor: '#162525',
                searchBgColor: '#162525',
                contentBgColor: '#162525',
                pageBgColor: '#162525',
                textColor: '#FFFFFF',
                queryTextColor: '#FFFFFF',
                outlineColor: '#44444'
            }"              
            />
            <VueDaumPostcode
            style='width:80vw;max-height:80vh; 
            backgroudColor:red;position:fixed; 
            top:50%;left:50%; 
            overflow:auto;
            font-size:10px;
            transform: translate(-50%,-50%);
            ' 
            id = 'mobilelocationSearch'
            @complete='result=$event'
            :theme="{
                bgColor: '#162525',
                searchBgColor: '#162525',
                contentBgColor: '#162525',
                pageBgColor: '#162525',
                textColor: '#FFFFFF',
                queryTextColor: '#FFFFFF',
                outlineColor: '#44444'
            }"              
            />
            
        </div>
        <button @click='closeLocation' class='closeLocation'><i class="fas fa-backspace">취소</i></button>
    </div>
</template>

<script>
import {VueDaumPostcode} from 'vue-daum-postcode'
// import axios from 'axios'

export default {
    name:'Location',
    components:{
        VueDaumPostcode,
                
    },
    data: function() {
        return {
            serverLatitude:0,
            serverLongitude:0,  
            userLatitude:0,
            userLongitude:0,
            result:'',    
            userBname:'',
        }
    },
    methods:{  
        geo: function(){
            let that = this
            var geocoder = new window.kakao.maps.services.Geocoder();
            var callback = function(result,status) {
                if (status === 'OK') {
                    that.userLatitude = result[0].address.y
                    that.userLongitude= result[0].address.x
                }   
            }
            geocoder.addressSearch(this.userBname,callback)          
        },
        closeLocation:function(){            
            this.$emit('selectAddress',this.result) 
        }
    },
    computed: {
    },
    props:{
        searchLocation:Boolean,
    },
    watch: {
        searchLocation:function(){
            let that = this
            
            if (navigator.geolocation && this.searchLocation) {
                navigator.geolocation.getCurrentPosition(function(position) {       
                that.serverLatitude = position.coords.latitude
                that.serverLongitude = position.coords.longitude
                alert('사용자위치정보 확인')
                });
            }
            
        },
        result: function(result){
            this.userBname = result.bname
            this.geo()
            this.$emit('selectAddress',result)            
        },
        userLatitude:function(){
            const a = Math.pow(this.serverLatitude-this.userLatitude,2)
            const b = Math.pow(this.serverLongitude-this.userLongitude,2)
            // 0.01로줄이기
            if (Math.sqrt(a+b)<=0.02) {
                this.$emit('checkAddress',true)
            } else {
                this.$emit('checkAddress',false)
                
            }
        }
    },
    mounted(){
        if (window.kakao && window.kakao.maps) {
            console.log('Welcome naite!')
        } else {
            const script = document.createElement('script');
            script.src= 'http://dapi.kakao.com/v2/maps/sdk.js?appkey=e990ec2518ab112eae22d81bcd858753&libraries=services'
            document.head.appendChild(script)
        }
    }

    
}
</script>

<style>
#location {
    position: relative;
    width: 200px;
    height:200px;
    background-color: transparent;
    z-index: 3;
    transform: trnaslate(-50%,-50%);
}

#locationSearch::-webkit-scrollbar{
    display: none;
}

.closeLocation {
    position:absolute;
    top:0;
    left:0;
    background-color: transparent;
    border:none;
    outline: none;
    display:inline-block
}

.closeLocation > i {
    display:inline-block;
    position:absolute;
    color: red;
    font-size: 32px;
    width: 400px;
    text-align: left;
    top:10px;
    left:10px;
    
}

#mobilelocationSearch{
    display: none;
}
@media screen and (max-width: 501px){
    #location {
    position: relative;
    width: 10vw;
    height:100vh;
    background-color: transparent;
    z-index: 3;
    transform:none;
    }
    #mobilelocationSearch{
        display: block;
    }
    #mobilelocationSearch::-webkit-scrollbar{
        display:none;
    }
    #locationSearch{
        display: none;
    }

    .closeLocation {
        position:fixed;
        top:5%;
        left:5%;
        background-color: black;
    }

    .closeLocation > i {
        position:relative;
        color: white;
        font-size: 15px;
        width: 100%;
        text-align: left;
        top:0;
        left:0;
        
    }
}


</style>
