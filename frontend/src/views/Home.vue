<template>
  <div class="home">
      <header>
        <h3>Welcome to Bookworm!</h3>
      </header>
      <div id="homeImages">
        <b-carousel
        id="carousel"
        v-model="slide"
        :interval="4000"
        indicators
        no-hover-pause
      >   
          <div class="innerCarousel">
            <b-carousel-slide class="slides" img-src="@/assets/images/girlreading.jpg"></b-carousel-slide>
            <b-carousel-slide class="slides" img-src="@/assets/images/woman-reading.jpg"></b-carousel-slide>
            <b-carousel-slide class="slides" img-src="@/assets/images/boyreadingbed.jpg"></b-carousel-slide>
            <b-carousel-slide class="slides" img-src="@/assets/images/boylaughbook.jpg"></b-carousel-slide>
            <b-carousel-slide class="slides" img-src="@/assets/images/manreading.jpg"></b-carousel-slide>
            <b-carousel-slide class="slides" img-src="@/assets/images/girlreadingcouch.jpg"></b-carousel-slide>
          </div>
        </b-carousel>
    </div>
    <div v-if="!currentUser" class="homeButtons">
      <div id="registerButton">
        <!-- s.stier 4/12/2020 changed route from register to registerfamily to support family registration -->
        <router-link :to="{ name: 'registerfamily' }" id="b-button" class="btn btn-lg btn-info btn-block"> Create  Your Family Account Now!</router-link>
      </div>
      <div v-if="!currentUser" id="loginButton">
        <router-link :to="{ name: 'login' }" id="b-button" class="btn btn-lg btn-info btn-block"> Returning Member: Login Here!</router-link>
      </div>
    </div>
    <div id="welcomeMessage">
        <h5>Make reading fun and interactive for your family!</h5>
        <p>With Bookworm’s award winning reading tracking system, you can stay on top and be informed about your children’s reading activity. Easily check in on your families reading time in a fun and engaging way. Easily create prizes to incentivize reading in your family, and make reading fun again! With BookWorm’s easy to use prize system, you can create custom awards for completing your reading assignments for the day.</p>
    </div>

  <footer>
    <ul id="imageCredit">
        <li class="credit"><a style="background-color:black;color:white;text-decoration:none;padding:4px 6px;font-family:-apple-system, BlinkMacSystemFont, &quot;San Francisco&quot;, &quot;Helvetica Neue&quot;, Helvetica, Ubuntu, Roboto, Noto, &quot;Segoe UI&quot;, Arial, sans-serif;font-size:12px;font-weight:bold;line-height:1.2;display:inline-block;border-radius:3px" href="https://unsplash.com/@joshapplegate?utm_medium=referral&amp;utm_campaign=photographer-credit&amp;utm_content=creditBadge" target="_blank" rel="noopener noreferrer" title="Download free do whatever you want high-resolution photos from Josh Applegate"><span style="display:inline-block;padding:2px 3px"><svg xmlns="http://www.w3.org/2000/svg" style="height:12px;width:auto;position:relative;vertical-align:middle;top:-2px;fill:white" viewBox="0 0 32 32"><title>unsplash-logo</title><path d="M10 9V0h12v9H10zm12 5h10v18H0V14h10v9h12v-9z"></path></svg></span><span style="display:inline-block;padding:2px 3px">Josh Applegate</span></a></li>
        <li class="credit"><a style="background-color:black;color:white;text-decoration:none;padding:4px 6px;font-family:-apple-system, BlinkMacSystemFont, &quot;San Francisco&quot;, &quot;Helvetica Neue&quot;, Helvetica, Ubuntu, Roboto, Noto, &quot;Segoe UI&quot;, Arial, sans-serif;font-size:12px;font-weight:bold;line-height:1.2;display:inline-block;border-radius:3px" href="https://unsplash.com/@bbh_singapore?utm_medium=referral&amp;utm_campaign=photographer-credit&amp;utm_content=creditBadge" target="_blank" rel="noopener noreferrer" title="Download free do whatever you want high-resolution photos from BBH Singapore"><span style="display:inline-block;padding:2px 3px"><svg xmlns="http://www.w3.org/2000/svg" style="height:12px;width:auto;position:relative;vertical-align:middle;top:-2px;fill:white" viewBox="0 0 32 32"><title>unsplash-logo</title><path d="M10 9V0h12v9H10zm12 5h10v18H0V14h10v9h12v-9z"></path></svg></span><span style="display:inline-block;padding:2px 3px">BBH Singapore</span></a></li>
        <li class="credit"><a style="background-color:black;color:white;text-decoration:none;padding:4px 6px;font-family:-apple-system, BlinkMacSystemFont, &quot;San Francisco&quot;, &quot;Helvetica Neue&quot;, Helvetica, Ubuntu, Roboto, Noto, &quot;Segoe UI&quot;, Arial, sans-serif;font-size:12px;font-weight:bold;line-height:1.2;display:inline-block;border-radius:3px" href="https://unsplash.com/@benwhitephotography?utm_medium=referral&amp;utm_campaign=photographer-credit&amp;utm_content=creditBadge" target="_blank" rel="noopener noreferrer" title="Download free do whatever you want high-resolution photos from Ben White"><span style="display:inline-block;padding:2px 3px"><svg xmlns="http://www.w3.org/2000/svg" style="height:12px;width:auto;position:relative;vertical-align:middle;top:-2px;fill:white" viewBox="0 0 32 32"><title>unsplash-logo</title><path d="M10 9V0h12v9H10zm12 5h10v18H0V14h10v9h12v-9z"></path></svg></span><span style="display:inline-block;padding:2px 3px">Ben White</span></a></li>
        <li class="credit"><a style="background-color:black;color:white;text-decoration:none;padding:4px 6px;font-family:-apple-system, BlinkMacSystemFont, &quot;San Francisco&quot;, &quot;Helvetica Neue&quot;, Helvetica, Ubuntu, Roboto, Noto, &quot;Segoe UI&quot;, Arial, sans-serif;font-size:12px;font-weight:bold;line-height:1.2;display:inline-block;border-radius:3px" href="https://unsplash.com/@sevenshooterimage?utm_medium=referral&amp;utm_campaign=photographer-credit&amp;utm_content=creditBadge" target="_blank" rel="noopener noreferrer" title="Download free do whatever you want high-resolution photos from Seven Shooter"><span style="display:inline-block;padding:2px 3px"><svg xmlns="http://www.w3.org/2000/svg" style="height:12px;width:auto;position:relative;vertical-align:middle;top:-2px;fill:white" viewBox="0 0 32 32"><title>unsplash-logo</title><path d="M10 9V0h12v9H10zm12 5h10v18H0V14h10v9h12v-9z"></path></svg></span><span style="display:inline-block;padding:2px 3px">Seven Shooter</span></a></li>
        <li class="credit"><a style="background-color:black;color:white;text-decoration:none;padding:4px 6px;font-family:-apple-system, BlinkMacSystemFont, &quot;San Francisco&quot;, &quot;Helvetica Neue&quot;, Helvetica, Ubuntu, Roboto, Noto, &quot;Segoe UI&quot;, Arial, sans-serif;font-size:12px;font-weight:bold;line-height:1.2;display:inline-block;border-radius:3px" href="https://unsplash.com/@gabrielrana?utm_medium=referral&amp;utm_campaign=photographer-credit&amp;utm_content=creditBadge" target="_blank" rel="noopener noreferrer" title="Download free do whatever you want high-resolution photos from Gabriel Tovar"><span style="display:inline-block;padding:2px 3px"><svg xmlns="http://www.w3.org/2000/svg" style="height:12px;width:auto;position:relative;vertical-align:middle;top:-2px;fill:white" viewBox="0 0 32 32"><title>unsplash-logo</title><path d="M10 9V0h12v9H10zm12 5h10v18H0V14h10v9h12v-9z"></path></svg></span><span style="display:inline-block;padding:2px 3px">Gabriel Tovar</span></a></li>
        <li class="credit"><a style="background-color:black;color:white;text-decoration:none;padding:4px 6px;font-family:-apple-system, BlinkMacSystemFont, &quot;San Francisco&quot;, &quot;Helvetica Neue&quot;, Helvetica, Ubuntu, Roboto, Noto, &quot;Segoe UI&quot;, Arial, sans-serif;font-size:12px;font-weight:bold;line-height:1.2;display:inline-block;border-radius:3px" href="https://unsplash.com/@lordmaui?utm_medium=referral&amp;utm_campaign=photographer-credit&amp;utm_content=creditBadge" target="_blank" rel="noopener noreferrer" title="Download free do whatever you want high-resolution photos from Chris Benson"><span style="display:inline-block;padding:2px 3px"><svg xmlns="http://www.w3.org/2000/svg" style="height:12px;width:auto;position:relative;vertical-align:middle;top:-2px;fill:white" viewBox="0 0 32 32"><title>unsplash-logo</title><path d="M10 9V0h12v9H10zm12 5h10v18H0V14h10v9h12v-9z"></path></svg></span><span style="display:inline-block;padding:2px 3px">Chris Benson</span></a></li>
    </ul>
  </footer>
  </div>
  
</template>

<script>
import auth from '@/auth.js'

export default {
  name: 'home',
   props: {
    currentUser: Object
  },
  data() {
    return {
      user: auth.getUser(),
      slide: 0,
      sliding: null
    }
  },
  methods: {

  }
}

</script>

<style scoped>

html,body {
    height: 100%;
    background-color: #f5f5f5;
  }

.home{
  margin-left: 5%;
  margin-right: 5%;
}

header{
  display: flex;
  justify-content: center;
}

.welcome{
  justify-content: center;
}

.slides{
  width: 45%;
  max-height: 400px;
  overflow: hidden;
  border-radius: 15px;
  margin: 1%
}

.innerCarousel{
  width:100%;
  display: flex;
  justify-content: center;
}

.homeButtons{
  height: 100%;
  display: -ms-flexbox;
  display: flex;
  -ms-flex-align: space-between;
  align-items: space-between;
  padding: 40px;
  justify-content: center;
}

#registerButton, #loginButton{
  margin: 20px;
  align-content: center;
}

#imageCredit{
  list-style: none;
}

.credit{
  display: inline;
  margin: 2px;
}

footer{
  display: flex;
  justify-content: center;
  margin-top: 10%;
}


</style>