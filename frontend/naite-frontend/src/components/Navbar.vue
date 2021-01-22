<template>
    <nav id="navbar">
        <div class="nav-title">
            나의 이웃테두리: 나이테
        </div>
        <div class="nav-link">
            <a href="#">골목길</a>
            <a href="#">번화가</a>
            <a href="#">동사무소</a>
            <a href="#">수군수군</a>
            <a href="#">소리소문</a>
            <a href="#">장터</a>
            <a href="#">확성기</a>
        </div>
        <div class="nav-right">
            <div class="nav-search">아이콘넣을예정</div>
            <div class="nav-status">
                
            </div>
        </div>
        
    </nav>
    
</template>
<script>
export default {
    name:'Navbar',
    components:{
                
    },
    data: function() {
        return {
        }
    },
    methods:{
    },
    computed: {

    },
    
    
    created:{
        
    }
    
}
</script>

<style>

#navbar {
    position:fixed;
    background-color: rgb(240, 198, 198);
    width:98%;
    height:8%;
    margin: 0 1%;
    text-align: left;
    display: flex;
    justify-content: space-between;
}

.nav-right{
    display:flex;
}

.nav-status{
    background-color: red;
    overflow: hidden;
    background-image: url('../assets/cha2.png');
    background-size: 100%;
    background-repeat: no-repeat;
    background-position: center;
    width:70px;
    height:70px;
    border-radius: 100%;
    
}


</style>