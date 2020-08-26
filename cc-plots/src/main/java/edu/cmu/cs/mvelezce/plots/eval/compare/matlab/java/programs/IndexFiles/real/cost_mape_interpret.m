interpret_approaches = ["Comprex"; "LR & R200"];
interpret_cost = [43.8 112.9];
interpret_mape = [3.2 2.9];

not_interpret_approaches = ["RF & R200"];
not_interpret_cost = [108.0];
not_interpret_mape = [0.3];

plot(interpret_cost, interpret_mape, '+', not_interpret_cost, not_interpret_mape, 'x', 'LineWidth', 2 ,'MarkerSize',16);

title('Apache Lucene', 'Fontsize',80);
xlabel('Cost (minutes)');
ylabel('Accuracy (MAPE)');

xlim([40.0, 130.0]);
ylim([0.1, 5.0]);

set(gca,'FontSize',20);

dx = 0.4; dy = 0.2; 
text(interpret_cost+dx, interpret_mape+dy, interpret_approaches, 'Fontsize', 16);
text(not_interpret_cost+dx, not_interpret_mape+dy, not_interpret_approaches, 'Fontsize', 16);

legend('Interpretable','Not interpretable','Location','northwest');

dim = [.15 .2 .2 .2];
str = {'LR: Linear Regression'; 'RF: Random Forest'; 'R200: 200 random configurations'};
annotation('textbox',dim,'String',str,'FitBoxToText','on','Fontsize', 16);

set(gcf,'Position',[100 100 550 400])

saveas(gcf,'IndexFiles.pdf');