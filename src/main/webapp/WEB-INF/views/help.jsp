<script>
	$(document).ready(function() {

		$('.faq2').hide();

		$('li.faq1').click(function() {
			$('.faq2').slideUp();
			$(this).nextUntil('.faq1').slideDown();
		});

	});
</script>


<ul>
 <li class="faq1">TSA</li>
 <li class="faq2">
  <ul class="faq">
   <li class="question">What is TSA?</li>
   <li class="answer">TSA stands for Trading Strategy Analyser. It is a program that allows you to try out trading strategies against historical data.</li>
  </ul>
 </li>
 <li class="faq1">Trading strategies</li>
 <li class="faq2">
  <ul class="faq">
   <li class="question">What are strategies?</li>
   <li class="answer">Strategies are lists of rules. Each rule gets run for every point of data and can trigger a trade.</li>
  </ul>
 </li>
 <li class="faq2">
  <ul class="faq">
   <li class="question">If my strategy has more than one rule, which one gets run?</li>
   <li class="answer">Every rule in a strategy gets run for every data point. That means you can get more than one trade triggered by a single point.</li>
  </ul>
 </li>
 <li class="faq1">Datasets</li>
 <li class="faq2">
  <ul class="faq">
   <li class="question">What is a dataset?</li>
   <li class="answer">A dataset consists of data points. Each data point represents the price of a given instrument (e.g. share) at a certain time.</li>
  </ul>
 </li>
 <li class="faq2">
  <ul class="faq">
   <li class="question">Where do I get datasets from?</li>
   <li class="answer">TSA comes with many datasets pre-loaded. But if you wish you can add more using the dataset upload page.</li>
  </ul>
 </li>
</ul>