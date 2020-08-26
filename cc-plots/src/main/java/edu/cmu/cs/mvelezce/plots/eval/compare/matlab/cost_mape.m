systems = ["Lucene"; "H2"; "Berkeley DB"; "Density Conv."];
comprex_cost = [43.8 31.9 41.4 25.1];
comprex_mape = [3.2 2.9 5.0 10.6];

rd200rf_cost = [108 66 36.4 40.4];
rd200rf_mape = [0. 0.7 1.1 5.5];

plot(comprex_cost, comprex_mape, '+', rd200rf_cost, rd200rf_mape, 'x', 'MarkerSize',16)

title('Comprex (+) vs. RF & R200 (x)', 'Fontsize',80);
xlabel('Cost (m)');
ylabel('MAPE');

xlim([22.0, 122.0]);
ylim([-0.5, 12.0]);

set(gca,'FontSize',20);

dx = 0.7; dy = 0.3; 
text(comprex_cost+dx, comprex_mape+dy, systems, 'Fontsize', 16);
text(rd200rf_cost+dx, rd200rf_mape+dy, systems, 'Fontsize', 16);

set(gcf,'Position',[100 100 550 400])