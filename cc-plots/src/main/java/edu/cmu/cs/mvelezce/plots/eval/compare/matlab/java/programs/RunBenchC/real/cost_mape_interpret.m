interpret_approaches = ["Comprex"; "R200 & LR"; "R50 & LR"; "FW & LR"];
interpret_cost = [31.9 110.6 16.5 2.4];
interpret_mape = [2.9 93.9 124.1 129.3];

not_interpret_approaches = ["R50 & RF"; "R200 & RF"; "FW & RF"; "PW & RF"];
not_interpret_cost = [16.4 106.0 2.4 21.1];
not_interpret_mape = [6.5 0.7 119 124.6];

plot(interpret_cost, interpret_mape, '+', not_interpret_cost, not_interpret_mape, 'x', 'LineWidth', 2 ,'MarkerSize',16);

title('H2', 'Fontsize',80);
xlabel('Cost (minutes)');
ylabel('Accuracy (MAPE)');

xlim([0.0, 145.0]);
ylim([-3.0, 150.0]);

set(gca,'FontSize',20);

dx = 2.0; dy = 3.0; 
text(interpret_cost+dx, interpret_mape+dy, interpret_approaches, 'Fontsize', 20);

dx = 2.0; dy = 5.0;
text(not_interpret_cost+dx, not_interpret_mape+dy, not_interpret_approaches, 'Fontsize', 20);

dim = [.15 .3 .2 .2];
str = {'LR: Linear Regression'; 'RF: Random Forest'; 'R200: 200 random configurations'};
%annotation('textbox',dim,'String',str,'FitBoxToText','on','Fontsize', 16);

%legend('Interpretable','Not interpretable','Location','northwest');

set(gcf,'Position',[100 100 550 400])

saveas(gcf,'RunBenchC.pdf');